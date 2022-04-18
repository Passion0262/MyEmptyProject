package com.example.logger.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String applog(@RequestBody String mockLog){
        //落盘
        log.info(mockLog);
        //根据日志类型，发送到kafka不同主题中去
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(mockLog);
            JsonNode startJson = rootNode.path("type");
            log.info(startJson.toString());
//            kafkaTemplate.send("日志标题",startJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mockLog;
    }

}
