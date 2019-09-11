package com.berry.oss.security.core.service.impl;

import com.berry.oss.security.core.entity.UserRole;
import com.berry.oss.security.core.mapper.UserRoleMapper;
import com.berry.oss.security.core.service.IUserRoleDaoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联关系 服务实现类
 * </p>
 *
 * @author HiCooper
 * @since 2019-09-11
 */
@Service
public class UserRoleDaoServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleDaoService {

}
