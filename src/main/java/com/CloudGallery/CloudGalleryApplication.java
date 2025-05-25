package com.CloudGallery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.cloudGallery.mapper")
@EnableAspectJAutoProxy
public class CloudGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGalleryApplication.class, args);
    }

}
