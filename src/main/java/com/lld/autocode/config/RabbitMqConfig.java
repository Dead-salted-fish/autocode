package com.lld.autocode.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.jx3.hmd.entity.HmdPersonal;
import com.lld.autocode.security.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/12 16:36
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }


    @RabbitListener(queues = "testqueue")
    @RabbitListener(queues = "testqueue1")
    private void testRedisLisiter(Message message) throws JsonProcessingException {
        String returnMessageBody = new String(message.getBody());
        HmdPersonal hmdPersonal = new ObjectMapper().readValue(returnMessageBody, HmdPersonal.class);
        System.out.println(hmdPersonal);
        System.out.println(message.getMessageProperties());
    }
}
