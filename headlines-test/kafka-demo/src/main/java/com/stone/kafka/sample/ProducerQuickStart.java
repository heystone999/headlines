package com.stone.kafka.sample;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerQuickStart {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // kafka链接配置信息
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key和value的序列化
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // ack配置
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        prop.put(ProducerConfig.RETRIES_CONFIG, 10);
        // 数据压缩
        prop.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        // 创建kafka producer对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);

        // 发送消息 topic key value
        ProducerRecord<String, String> kvProducerRecord = new ProducerRecord<String, String>("topic-first", "hello kafka");
        // 同步发送消息
//        RecordMetadata recordMetadata = producer.send(kvProducerRecord).get();
//        System.out.println(recordMetadata.offset());
        // 异步发送消息
        producer.send(kvProducerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    System.out.println("记录异常信息到日志中");
                }
                System.out.println(recordMetadata.offset());
            }
        });

        // 关闭消息通道, 必须关闭, 否则消息发送不成功
        producer.close();
    }
}