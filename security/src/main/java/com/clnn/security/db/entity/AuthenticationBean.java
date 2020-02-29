package com.clnn.security.db.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationBean implements Serializable {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String vcode;
}
