package com.example.kafkatest.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author ：shadow
 * @date ：Created in 2022/5/7 10:17
 */
public class MyKafkaConsumer extends Thread{

    KafkaConsumer<Integer, String> consumer;
    String topic;

    public MyKafkaConsumer(String topic) {
        // 构建连接配置，这里是ConsumerConfig
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "123.60.32.74:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "my-consumer");
        // 反序列化，这里是Deserializer
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        // 以下是Producer没有的配置
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-gid"); // 要加入的group
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000"); // 超时，心跳
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 自动提交（批量）
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 新group消费为位置

        consumer = new KafkaConsumer<>(properties);
        this.topic = topic;
    }

    public void run() {
        // 死循环不断消费消息
        while (true) {
            // 绑定订阅主题
            // 注：Collections.singleton返回一个单元素&不可修改Set集合，
            // 同样的还有singletonList，singletonMap
            consumer.subscribe(Collections.singleton(this.topic));
            // 接收消息 POLL()！！！
            ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            // 注：一行的lamada表达式可以不用{}
            consumerRecords.forEach(record -> System.out.println(record.key() + "->" +
                    record.value() + "->" + record.offset()));
        }
    }

    public static void main(String[] args) {
        // 拉取test主题的消息
        new MyKafkaConsumer("gmall_start_bak").start();
    }
}
