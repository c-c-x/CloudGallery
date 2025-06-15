# MinIO 工具类使用说明文档

## 文档信息

* **文档名称** ：MinIO 工具类使用说明
* **版本号** ：1.0
* **创建日期** ：2025 年 6 月 15 日
* **适用范围** ：Java 开发人员
* **关联系统** ：MinIO 对象存储服务

## 目录

* [一、工具类概述]()
* [二、初始化配置]()
  * [1. 构造方法]()
  * [2. 初始化示例]()
* [三、存储桶（Bucket）操作]()
  * [1. 创建存储桶]()
  * [2. 检查存储桶是否存在]()
  * [3. 获取存储桶策略]()
  * [4. 获取所有存储桶列表]()
  * [5. 根据名称获取存储桶]()
  * [6. 删除存储桶]()
* [四、文件（Object）操作]()
  * [1. 检查文件是否存在]()
  * [2. 检查文件夹是否存在]()
  * [3. 根据前缀查询文件]()
  * [4. 获取文件流]()
  * [5. 断点下载]()
  * [6. 上传 MultipartFile 文件]()
  * [7. 上传本地文件]()
  * [8. 通过流上传文件]()
  * [9. 创建文件夹]()
  * [10. 获取文件状态信息]()
  * [11. 拷贝文件]()
  * [12. 删除文件]()
  * [13. 批量删除文件]()
* [五、文件外链生成]()
  * [1. 生成带过期时间的外链]()
  * [2. 生成永久外链（需存储桶公开）]()
* [六、工具方法]()
* [七、完整使用示例]()
* [八、注意事项]()

## 一、工具类概述

`MinIOUtils` 是一个基于 MinIO 对象存储服务的 Java 工具类，提供了对 MinIO 存储桶（Bucket）和对象（Object）的完整操作功能，包括存储桶管理、文件上传下载、文件删除、权限管理等。该工具类通过配置 MinIO 服务器连接信息，实现与 MinIO 服务的交互，适用于需要集成 MinIO 存储功能的 Java 应用程序。

## 二、初始化配置

### 1. 构造方法

java

```java
public MinIOUtils() {}

public MinIOUtils(String endpoint, String fileHost, String bucketName, String accessKey, String secretKey)
```

 **参数说明** ：

* `endpoint`：MinIO 服务器地址（如 `http://minio.example.com:9000`）
* `fileHost`：文件访问域名（用于生成文件访问 URL）
* `bucketName`：默认存储桶名称
* `accessKey`：MinIO 访问密钥
* `secretKey`：MinIO 秘密密钥

### 2. 初始化示例

java

```java
MinIOUtils minIOUtils = new MinIOUtils(
    "http://minio.example.com:9000",
    "minio.example.com:9000",
    "my-bucket",
    "ACCESS_KEY",
    "SECRET_KEY"
);
```

## 三、存储桶（Bucket）操作

### 1. 创建存储桶

java

```java
private static void createBucket(String bucketName) throws Exception
```

* **功能** ：检查存储桶是否存在，不存在则创建
* **参数** ：`bucketName` - 存储桶名称
* **示例** ：`minIOUtils.createBucket("images");`

### 2. 检查存储桶是否存在

java

```java
public static boolean bucketExists(String bucketName) throws Exception
```

* **返回值** ：`true`（存在）/ `false`（不存在）
* **示例** ：`boolean exists = MinIOUtils.bucketExists("images");`

### 3. 获取存储桶策略

java

```java
public static String getBucketPolicy(String bucketName) throws Exception
```

* **返回值** ：存储桶的访问策略 JSON 字符串
* **示例** ：`String policy = MinIOUtils.getBucketPolicy("images");`

### 4. 获取所有存储桶列表

java

```java
public static List<Bucket> getAllBuckets() throws Exception
```

* **返回值** ：存储桶列表 `List<Bucket>`
* **示例** ：`List<Bucket> buckets = MinIOUtils.getAllBuckets();`

### 5. 根据名称获取存储桶

java

```java
public static Optional<Bucket> getBucket(String bucketName) throws Exception
```

* **返回值** ：`Optional<Bucket>`（存在时返回存储桶对象）
* **示例** ：`Optional<Bucket> bucket = MinIOUtils.getBucket("images");`

### 6. 删除存储桶

java

```java
public static void removeBucket(String bucketName) throws Exception
```

* **注意事项** ：存储桶必须为空才能删除
* **示例** ：`MinIOUtils.removeBucket("images");`

## 四、文件（Object）操作

### 1. 检查文件是否存在

java

```java
public static boolean isObjectExist(String bucketName, String objectName)
```

* **返回值** ：`true`（存在）/ `false`（不存在）
* **示例** ：`boolean exist = MinIOUtils.isObjectExist("images", "logo.png");`

### 2. 检查文件夹是否存在

java

```java
public static boolean isFolderExist(String bucketName, String objectName)
```

* **实现原理** ：通过检查是否存在空文件（模拟文件夹）
* **示例** ：`boolean exist = MinIOUtils.isFolderExist("images", "2023/");`

### 3. 根据前缀查询文件

java

```java
public static List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws Exception
```

* **参数** ：
* `prefix` - 文件前缀（如 `2023/`）
* `recursive` - 是否递归查询子目录
* **返回值** ：文件列表 `List<Item>`
* **示例** ：`List<Item> items = MinIOUtils.getAllObjectsByPrefix("images", "2023/", true);`

### 4. 获取文件流

java

```java
public static InputStream getObject(String bucketName, String objectName) throws Exception
```

* **返回值** ：文件输入流 `InputStream`
* **示例** ：`InputStream stream = MinIOUtils.getObject("images", "logo.png");`

### 5. 断点下载

java

```java
public InputStream getObject(String bucketName, String objectName, long offset, long length) throws Exception
```

* **参数** ：
* `offset` - 起始字节位置
* `length` - 读取长度
* **示例** ：`InputStream stream = minIOUtils.getObject("images", "video.mp4", 1024, 1024*1024);`

### 6. 上传 MultipartFile 文件

java

```java
public static ObjectWriteResponse uploadFile(String bucketName, MultipartFile file, String objectName, String contentType) throws Exception
```

* **参数** ：
* `file` - Spring MultipartFile 文件对象
* `contentType` - 文件 MIME 类型（如 `image/png`）
* **返回值** ：上传响应 `ObjectWriteResponse`
* **示例** ：

java

```java
ObjectWriteResponse response = MinIOUtils.uploadFile(
    "images", file, "2023/logo.png", "image/png"
);
```

### 7. 上传本地文件

java

```java
public static String uploadFile(String bucketName, String objectName, String fileName, boolean needUrl) throws Exception
```

* **参数** ：
* `fileName` - 本地文件路径
* `needUrl` - 是否返回访问 URL
* **返回值** ：文件访问 URL（`needUrl=true`时）
* **示例** ：`String url = MinIOUtils.uploadFile("images", "2023/logo.png", "/local/path/logo.png", true);`

### 8. 通过流上传文件

java

```java
public static ObjectWriteResponse uploadFile(String bucketName, String objectName, InputStream inputStream) throws Exception
public static String uploadFile(String bucketName, String objectName, InputStream inputStream, boolean needUrl) throws Exception
```

* **示例** ：

java

```java
// 方式1：返回上传响应
ObjectWriteResponse response = MinIOUtils.uploadFile("images", "logo.png", inputStream);
// 方式2：返回访问URL
String url = MinIOUtils.uploadFile("images", "logo.png", inputStream, true);
```

### 9. 创建文件夹

java

```java
public static ObjectWriteResponse createDir(String bucketName, String objectName) throws Exception
```

* **实现原理** ：上传一个 0 字节的空文件作为文件夹标识
* **示例** ：`MinIOUtils.createDir("images", "2023/");`

### 10. 获取文件状态信息

java

```java
public static String getFileStatusInfo(String bucketName, String objectName) throws Exception
```

* **返回值** ：文件元数据信息字符串
* **示例** ：`String info = MinIOUtils.getFileStatusInfo("images", "logo.png");`

### 11. 拷贝文件

java

```java
public static ObjectWriteResponse copyFile(String bucketName, String objectName, String srcBucketName, String srcObjectName) throws Exception
```

* **参数** ：
* `bucketName, objectName` - 目标存储桶和文件名
* `srcBucketName, srcObjectName` - 源存储桶和文件名
* **示例** ：`MinIOUtils.copyFile("images", "new/logo.png", "backup", "old/logo.png");`

### 12. 删除文件

java

```java
public static void removeFile(String bucketName, String objectName) throws Exception
```

* **示例** ：`MinIOUtils.removeFile("images", "logo.png");`

### 13. 批量删除文件

java

```java
public static void removeFiles(String bucketName, List<String> keys)
```

* **参数** ：`keys` - 需要删除的文件路径列表
* **示例** ：

java

```java
List<String> keys = Arrays.asList("2023/image1.png", "2023/image2.png");
MinIOUtils.removeFiles("images", keys);
```

## 五、文件外链生成

### 1. 生成带过期时间的外链

java

```java
public static String getPresignedObjectUrl(String bucketName, String objectName, Integer expires) throws Exception
```

* **参数** ：`expires` - 外链过期时间（秒，最大 7 天）
* **示例** ：`String url = MinIOUtils.getPresignedObjectUrl("images", "logo.png", 3600);`（1 小时有效期）

### 2. 生成永久外链（需存储桶公开）

java

```java
public static String getPresignedObjectUrl(String bucketName, String objectName) throws Exception
```

* **注意事项** ：存储桶需设置为公开可读
* **示例** ：`String url = MinIOUtils.getPresignedObjectUrl("images", "logo.png");`

## 六、工具方法

### URL 解码转换

java

```java
public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException
```

* **功能** ：将 URL 编码字符串转换为 UTF-8 格式
* **示例** ：`String decoded = MinIOUtils.getUtf8ByURLDecoder("hello%20world");`

## 七、完整使用示例

java

```java
// 初始化工具类
MinIOUtils minIOUtils = new MinIOUtils(
    "http://minio.example.com:9000",
    "minio.example.com:9000",
    "my-bucket",
    "ACCESS_KEY",
    "SECRET_KEY"
);

// 1. 检查存储桶是否存在，不存在则创建
if (!MinIOUtils.bucketExists("images")) {
    minIOUtils.createBucket("images");
}

// 2. 上传文件
MultipartFile file = ...; // 从请求中获取文件
ObjectWriteResponse response = MinIOUtils.uploadFile(
    "images", file, "2023/logo.png", "image/png"
);

// 3. 生成访问外链
String url = MinIOUtils.getPresignedObjectUrl("images", "2023/logo.png", 86400); // 24小时有效期

// 4. 下载文件
InputStream stream = MinIOUtils.getObject("images", "2023/logo.png");

// 5. 删除文件
MinIOUtils.removeFile("images", "2023/logo.png");
```
