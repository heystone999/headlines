package com.stone.xxljob.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloJob {
    @Value("${server.port}")
    private String port;

    @XxlJob("demoJobHandler")
    public void helloJob() {
        System.out.println("执行xxl-job" + port);
    }
}
