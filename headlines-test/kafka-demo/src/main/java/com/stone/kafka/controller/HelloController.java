package com.stone.kafka.controller;

import com.alibaba.fastjson.JSON;
import com.stone.kafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/hello")
    public String hello() {
//        kafkaTemplate.send("stone-topic", "STONE");
        User user = new User();
        user.setUsername("stone");
        user.setAge(18);
        kafkaTemplate.send("user-topic", JSON.toJSONString(user));
        return "ok";
    }
}
