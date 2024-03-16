package com.stone.minio.test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MinIOTest {

    /**
     * 把list.html文件上传到minio中，并且可以在浏览器中访问
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("headlines-test/freemarker-demo/list.html");
            MinioClient minioClient = MinioClient.builder().credentials("minioadmin", "minioadmin").endpoint("http://localhost:9000").build();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("list.html")
                    .contentType("text/html")
                    .bucket("headlines")
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);

            System.out.println("http://localhost:9000/headlines/list.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
