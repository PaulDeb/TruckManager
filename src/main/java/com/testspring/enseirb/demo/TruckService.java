package com.testspring.enseirb.demo;

import java.util.Map;
import java.util.Stack;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TruckService {
    private final Map<Integer, Stack<TruckPosition>> trucks;
    private final RestTemplate restTemplate = new RestTemplate();

    public TruckService() {
        trucks = new java.util.HashMap<>();
    }

    public void addTruckPosition(TruckPosition truckPosition) {
        int truckId = truckPosition.getTruckId();
        Stack<TruckPosition> truckPositions = trucks.get(truckId);
        if (truckPositions == null) {
            truckPositions = new Stack<>();
            trucks.put(truckId, truckPositions);
        }
        truckPositions.add(truckPosition);
    }

    public TruckPosition getLastTruckPosition(int truckId) {
        Stack<TruckPosition> truckPositions = trucks.get(truckId);
        if (truckPositions == null) {
            return null;
        }
        return truckPositions.peek();
    }

    public ModelAndView getTruckMap(int truckId) {
        String url = "http://breisen.datamix.ovh:8080/map";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        
        TruckPosition lastTruckPosition = getLastTruckPosition(truckId);
        if (lastTruckPosition == null) {
            return null;
        }

        Position position = lastTruckPosition.getPosition();
        
        HttpEntity<Position> entity = new HttpEntity<>(position, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ModelAndView mav = new ModelAndView("redirect:" + response.getBody());
            return mav;
        }
        else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("error");
            mav.addObject("Error while getting map ", response.getStatusCode());
            return mav;  
        }
    }
}
