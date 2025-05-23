package com.CloudGallery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cloudGallery.mapper")
public class CloudGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGalleryApplication.class, args);
    }

}
