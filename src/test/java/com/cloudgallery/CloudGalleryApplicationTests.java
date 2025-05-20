package com.cloudgallery;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.cloudgallery.domain.User;
import com.cloudgallery.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudGalleryApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        JWTCreator.Builder builder = JWT.create()
                .withClaim("id", 1)
                .withClaim("username", "admin")
                .withClaim("password", "123456");
        System.out.println(builder);
    }


    @Test
    void saveUser() {
        User user = User.builder()
                .userName("test_user_001") // 用户名（唯一标识）
                .password("e10adc3949ba59abbe56e057f20f883e") // 密码（示例为 MD5 加密后的 "123456"）
                .nickName("测试用户小云") // 昵称
                .phone("13812345678") // 手机号（符合大陆手机号格式）
                .email("test_user@cloudgallery.com") // 邮箱
                .gender(1) // 性别（1: 男，2: 女，0: 未知）
                .idNumber("110101199001011234") // 身份证号（符合18位格式）
                .status(1) // 用户状态（1: 正常，0: 禁用）
                .build();

        userService.save(user);
    }

    @Test
    void getUser() {
        User user = userService.getById(1);
        System.out.println(user);
    }
}
