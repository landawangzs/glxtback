package com.zs.user.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 @author zs

 用户请求体

 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1804599576803838117L;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;

}
