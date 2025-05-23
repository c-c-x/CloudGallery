package com.CloudGallery;

import com.CloudGallery.common.utils.MinIOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // 可选：指定测试环境配置
public class MinIOConnectionTest {

    @Autowired
    private MinIOUtils minIOUtils;

    /**
     * 测试 MinIO 客户端是否创建成功
     */
    @Test
    void testMinioClientCreation() {
        assertNotNull(minIOUtils, "MinIOUtils Bean 未正确注入");
        
        // 检查 MinioClient 是否初始化成功
        assertNotNull(MinIOUtils.minioClient, "MinioClient 未创建");
    }

    /**
     * 测试 Bucket 是否存在（验证连接有效性）
     */
    @Test
    void testBucketExistence() {
        try {
            // 使用配置中的 bucketName 进行验证
            String bucketName = MinIOUtils.bucketName;
            
            // 检查 Bucket 是否存在（创建 MinIO 客户端时会自动创建 Bucket）
            boolean exists = MinIOUtils.bucketExists(bucketName);
            assertTrue(exists, "Bucket 不存在，连接可能失败");
            
        } catch (Exception e) {
            fail("检查 Bucket 存在时抛出异常：" + e.getMessage());
        }
    }

    /**
     * 测试简单文件操作（可选，验证读写权限）
     */
    @Test
    void testSimpleFileOperation() {
        String testObjectName = "test-file.txt";
        String testContent = "Hello, MinIO!";

        try {
            // 上传测试文件
            MinIOUtils.uploadFile(
                    MinIOUtils.bucketName,
                    testObjectName,
                    new ByteArrayInputStream(testContent.getBytes())
            );

            // 验证文件存在
            assertTrue(MinIOUtils.isObjectExist(MinIOUtils.bucketName, testObjectName), "文件上传后未找到");

            // 删除测试文件
            MinIOUtils.removeFile(MinIOUtils.bucketName, testObjectName);

            // 验证文件已删除
            assertFalse(MinIOUtils.isObjectExist(MinIOUtils.bucketName, testObjectName), "文件删除后仍存在");

        } catch (Exception e) {
            fail("文件操作测试失败：" + e.getMessage());
        }
    }
}