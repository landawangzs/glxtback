package com.zs.user.model.domain.request;

import lombok.Data;

//import java.io.Serial;
import java.io.Serializable;

/**
 *@author zs
 *用户请求体

 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1804599576803838117L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;

}
