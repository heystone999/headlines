package com.stone.minio.test;

import com.stone.file.service.FileStorageService;
import com.stone.minio.MinIOApplication;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
public class MinIOTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void test() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("../freemarker-demo/list.html");
        String path = fileStorageService.uploadHtmlFile("", "list.html", fileInputStream);
        System.out.println(path);
    }

    /**
     * 把list.html文件上传到minio中，并且可以在浏览器中访问
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("headlines-test/freemarker-demo/index.js");
            MinioClient minioClient = MinioClient.builder().credentials("minioadmin", "minioadmin").endpoint("http://localhost:9000").build();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("plugins/js/index.js")
                    .contentType("text/js")
                    .bucket("headlines")
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);

//            System.out.println("http://localhost:9000/headlines/list.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
