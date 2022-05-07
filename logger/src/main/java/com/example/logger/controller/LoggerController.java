package com.example.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：shadow
 * @date ：Created in 2022/4/15 10:22
 */
//@RestController = @Controller + @RequestBody
@RestController
@Slf4j
public class LoggerController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
    public String applog(@RequestBody String jsonLog){
        //落盘
        log.info(jsonLog);
        //根据日志类型，发送到kafka不同主题中去
//        JSONObject jsonObject = JSON.parseObject(jsonLog);
//        if(jsonObject.getJSONObject("start")!=null){
//        //启动日志
//            kafkaTemplate.send("gmall_start_bak",jsonLog);
//        }else{
//        //事件日志
//            kafkaTemplate.send("gmall_event_bak",jsonLog);
//        }
        kafkaTemplate.send("gmall_start_bak",jsonLog);
        return "success";
    }

    @RequestMapping("/send")
    public boolean send(@RequestParam String message) {
        try {
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("gmall_start_bak", message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.err.println("wtopic04 - 生产者 发送消息失败：" + throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    System.out.println("wtopic04 - 生产者 发送消息成功：" + stringObjectSendResult.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}