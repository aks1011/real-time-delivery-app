package com.devalok.kafka.real_time_delivery_app.kafka;

import com.devalok.kafka.real_time_delivery_app.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerLocationProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public ResponseEntity<String> updateLocation(String currentLocation){
        kafkaTemplate.send(Constants.DELIVERY_PARTNER_LOCATION, currentLocation);
        return ResponseEntity.ok("Current location updated!!!");
    }
}
