package com.CloudGallery;

import com.CloudGallery.domain.DTO.admin.UserListDTO;
import com.CloudGallery.domain.DTO.admin.UserPageDTO;
import com.CloudGallery.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

@SpringBootTest
class CloudGalleryApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        stringRedisTemplate.opsForValue().set("test", "test");
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }

    @Test
    void getROles(){
        Set<String> roles = userMapper.getRoles(1925822930197938176L);
        System.out.println(roles);
    }

    @Test
    void getUserList(){
        UserPageDTO userPageDTO = new UserPageDTO();
        userPageDTO.setUserName("2251600317");
        List<UserListDTO> userList = userMapper.getUserList(userPageDTO);
        System.out.println(userList);
    }
}
