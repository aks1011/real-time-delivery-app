package com.devalok.kafka.real_time_delivery_app.kafka;

import com.devalok.kafka.real_time_delivery_app.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerLocationConsumer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = Constants.DELIVERY_PARTNER_LOCATION, groupId = "RealTimeDeliveryApp")
    public void getCurrentLocation(String location){
        System.out.println(location);
    }
}
