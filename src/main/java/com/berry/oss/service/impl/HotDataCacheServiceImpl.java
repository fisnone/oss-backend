package com.berry.oss.service.impl;

import com.berry.oss.service.IHotDataCacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2019/11/29 14:41
 * fileName：HotDataCacheServiceImpl
 * Use：
 */
@Service
public class HotDataCacheServiceImpl implements IHotDataCacheService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ZSetOperations<String, String> zSetOperations;

    @Resource
    private HashOperations<String, String, String> hashOperations;

    private static final String HOT_DATA_KEY = "hot_data:";
    private static final String Z_SET_KEY = "hot_data_rank:";
    private static final int RANK_KEEP_SIZE = 20;

    @Override
    public InputStream getObjectIsByObjectId(String objectId) throws IOException {
        String object = hashOperations.get(HOT_DATA_KEY, objectId);
        zSetOperations.incrementScore(Z_SET_KEY, objectId, 1D);
        Long size = zSetOperations.zCard(Z_SET_KEY);
        if (size != null && size > RANK_KEEP_SIZE) {
            // 默认是 升序排，所以移除的是前部份
            Set<String> delRangeObjectIds = zSetOperations.range(Z_SET_KEY, 0, size - RANK_KEEP_SIZE - 1);
            hashOperations.delete(HOT_DATA_KEY, delRangeObjectIds);
            zSetOperations.remove(Z_SET_KEY, delRangeObjectIds);
        }
        if (StringUtils.isBlank(object)) {
            return null;
        }
        return new ByteArrayInputStream(object.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void setObject(String objectId, InputStream inputStream) throws IOException {
        if (inputStream.available() > 0) {
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);
            hashOperations.put(HOT_DATA_KEY, objectId, new String(bytes, StandardCharsets.UTF_8));
        }
    }
}
