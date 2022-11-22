package com.testspring.enseirb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TruckController {
    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping(value = "/truck/{id}")
    public TruckPosition getLastTruckPosition(@PathVariable int id) {
        TruckPosition lastTruckPosition = truckService.getLastTruckPosition(id);
        if (lastTruckPosition == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No known last position");
        }
        return lastTruckPosition;
    }

    @GetMapping(value = "/truck/{id}/map")
    public ModelAndView getTruckMap(@PathVariable int id) {
        ModelAndView mav = truckService.getTruckMap(id);
        if (mav == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No known last position");
        }
        return mav;
    } 
}
