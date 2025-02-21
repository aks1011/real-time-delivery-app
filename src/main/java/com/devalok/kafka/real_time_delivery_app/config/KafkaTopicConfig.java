package com.devalok.kafka.real_time_delivery_app.config;

import com.devalok.kafka.real_time_delivery_app.model.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic Topic(){
        return TopicBuilder.name(Constants.DELIVERY_PARTNER_LOCATION).build();
    }

}
