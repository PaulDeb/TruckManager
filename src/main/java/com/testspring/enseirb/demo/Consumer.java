package com.testspring.enseirb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired TruckService truckService; 
    @Autowired AlertService alertService;

    @KafkaListener(groupId = "paul-trucks", topics = "trucks.position")
    public void consume(TruckPosition truckPosition) {
        logger.info(String.format("TRUCK POSITION -> Consumed message -> %s", truckPosition));
        truckService.addTruckPosition(truckPosition);
        alertService.checkAndPublish(truckPosition);
    }
}
