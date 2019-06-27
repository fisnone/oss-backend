package com.berry.oss.module.mo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Title UpdateAccessKeyMo
 * Description
 * Copyright (c) 2019
 * Company  上海思贤信息技术股份有限公司
 *
 * @author berry_cooper
 * @version 1.0
 * @date 2019/6/27 18:26
 */
@Data
public class UpdateAccessKeyMo {

    @NotBlank
    private String accessKeyId;
}
