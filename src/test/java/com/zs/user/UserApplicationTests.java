package com.zs.user;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
@MapperScan("com.zs.user.Mapper")
@SpringBootTest
class UserApplicationTests {

    @Test
    void contextLoads() {
    }

}
