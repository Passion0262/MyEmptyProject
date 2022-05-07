package com.example.kafkatest.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author ：shadow
 * @date ：Created in 2022/5/7 10:09
 */
public class MyKafKaProducer extends Thread{

    KafkaProducer<Integer, String> producer;
    String topic; // 主题

    public MyKafKaProducer(String topic) {
        // 构建连接配置
        Properties properties = new Properties();

        // 若要配多个服务器，用逗号隔开
        // 注：服务器要开放端口，若云服务器还要在server.properties配置内网IP和外网IP
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "123.60.32.74:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "my-producer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        // 构造Client无非是：new 或 工厂模式
        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    public void run() {
        int num=0;
        while(num<50){
            String msg="pratice test message:"+num;
            System.out.println(msg);
            try {
                // 异步调用
                System.out.println("异步调用");
                producer.send(new ProducerRecord<>(topic, msg), new Callback() {
                    @Override
                    // 回调函数
                    // 注：这里其实还能通过lamada用函数式接口(metadata, exception) -> {}
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("callback: "+ recordMetadata.offset()
                                +"->"+recordMetadata.partition());
                    }
                });
                TimeUnit.SECONDS.sleep(2);
                num++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


//    public void run() {
//        int num = 0;
//        String msg = "kafka practice msg: " + num;
//        while (num < 20) {
//            try {
//                // 发送消息send()！！! 同步调用
//                // Future.get()会阻塞，等待返回结果......
//                RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topic, msg)).get();
//                // 等上面get到结果了，才能执行这里
//                System.out.println(recordMetadata.offset() + "->" + recordMetadata.partition() +
//                        "->" + recordMetadata.topic());
//                TimeUnit.SECONDS.sleep(2);
//                num++;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void main(String[] args) {
        // 传入gmall_start_bak主题
        new MyKafKaProducer("gmall_start_bak").start();
    }
}

