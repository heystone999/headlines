package com.stone.xxljob.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HelloJob {
    @Value("${server.port}")
    private String port;

    @XxlJob("demoJobHandler")
    public void helloJob() {
        System.out.println("执行xxl-job" + port);
    }

    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        List<Integer> list = getList();
        for (Integer integer : list) {
            if (integer % shardTotal == shardIndex) {
                System.out.println(shardIndex + "分片执行任务" + integer);
            }
        }
    }

    public List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        return list;
    }
}
