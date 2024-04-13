package com.zs.user.service;


import com.zs.user.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

/* 用户测试
* @author zs
* */
@MapperScan("com.zs.user.Mapper")
    @SpringBootTest
    public class UserServiceTest {
        @Resource
        private UserService userService;
        @Test
        public void testAddUser(){
            User user = new User();
            user.setUsername("zs");
            user.setUserAccount("12345678");
            user.setAvatarUrl("https://www.baidu.com/img/flexible/logo/pc/result.png");
            user.setGender(0);
            user.setUserPassword("23456789");
            user.setEmail("111");
            user.setPhone("111");


            boolean result = userService.save(user);
            System.out.println(user.getId());
            Assertions.assertTrue(result);
        }
    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}



