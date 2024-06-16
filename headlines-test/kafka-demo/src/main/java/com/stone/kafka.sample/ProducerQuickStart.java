package com.stone.kafka.sample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerQuickStart {
    public static void main(String[] args) {
        // kafka链接配置信息
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key和value的序列化
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 创建kafka producer对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);

        // 发送消息 topic key value
        ProducerRecord<String, String> kvProducerRecord = new ProducerRecord<String, String>("topic-first", "key-001", "hello kafka");
        producer.send(kvProducerRecord);

        // 关闭消息通道, 必须关闭, 否则消息发送不成功
        producer.close();
    }
}