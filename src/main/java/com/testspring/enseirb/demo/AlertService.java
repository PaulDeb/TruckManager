package com.testspring.enseirb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlertService {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void checkAndPublish(TruckPosition truckPosition) {
        logger.info("Checking truck position {}", truckPosition);

        String url = "http://breisen.datamix.ovh:8080/position/check";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");

        HttpEntity<TruckPosition> entity = new HttpEntity<>(truckPosition, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            int alertLevel = Integer.parseInt(response.getBody());
            if (alertLevel > 0) {
                logger.info("Alert level: {}", alertLevel);
                kafkaTemplate.send("trucks.alert", "" + alertLevel);
            }
        } else {
            logger.error("Error: {}", response);
        }
    }
}
