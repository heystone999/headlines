package com.stone.wemedia.test;

import com.stone.common.aliyun.GreenImageScan;
import com.stone.common.aliyun.GreenTextScan;
import com.stone.file.service.FileStorageService;
import com.stone.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {
    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private GreenImageScan greenImageScan;


    /**
     * 测试txt审核
     */
    @Test
    public void testScanText() throws Exception {
        Map map = greenTextScan.greeTextScan("我是一个好人");
        System.out.println(map);
    }

    /**
     * 测试image审核
     */
    @Test
    public void testScanImage() throws Exception {
        byte[] bytes = fileStorageService.downLoadFile("http://localhost:9000/headlines/2024/05/12/5509f9759aa8481ca5f845b340d3560f.jpg");
        List<byte[]> list = new ArrayList<>();
        list.add(bytes);
        Map map = greenImageScan.imageScan(list);
        System.out.println(map);
    }
}

