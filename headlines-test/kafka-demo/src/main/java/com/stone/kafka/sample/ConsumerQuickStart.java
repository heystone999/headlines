package com.stone.kafka.sample;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class ConsumerQuickStart {
    public static void main(String[] args) {
        // kafka的配置信息
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key和value的反序列化
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // 设置消费者组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        // 手动提交偏移量
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 创建kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);

        // 订阅主题
        consumer.subscribe(Collections.singletonList("topic-first"));

        // 拉取消息
        // 同步提交和异步提交偏移量
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println(consumerRecord.key());
                    System.out.println(consumerRecord.value());
                    System.out.println(consumerRecord.offset());
                    System.out.println(consumerRecord.partition());
                }
                // 异步
                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("记录错误的信息: " + e);
        } finally {
            // 同步
            consumer.commitSync();
        }

        /*while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key());
                System.out.println(consumerRecord.value());
                System.out.println(consumerRecord.offset());
                System.out.println(consumerRecord.partition());

//                try {
//                    // 同步提交偏移量
//                    consumer.commitSync();
//                } catch (CommitFailedException e) {
//                    System.out.println("记录提交失败的异常: " + e);
//                }
            }
            // 异步提交偏移量
//            consumer.commitAsync(new OffsetCommitCallback() {
//                @Override
//                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
//                    if (e != null) {
//                        System.out.println("记录错误的提交偏移量: " + map + "异常: " + e);
//                    }
//                }
//            });
        }*/
    }
}
