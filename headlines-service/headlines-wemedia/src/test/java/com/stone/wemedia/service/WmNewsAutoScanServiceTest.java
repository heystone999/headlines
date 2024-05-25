package com.stone.wemedia.service;

import com.stone.wemedia.WemediaApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
class WmNewsAutoScanServiceTest {
    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Test
    void autoScanWmNews() {
        wmNewsAutoScanService.autoScanWmNews(6234);
    }
}