package com.nltu.traffic_rest_controller.controller;

import com.nltu.traffic_rest_controller.entity.TrafficData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private TrafficData trafficData = new TrafficData();

    public TrafficController() {
        trafficData.setRed(5);
        trafficData.setGreen(15);
        trafficData.setYellow(2);
    }

    @PostMapping("/update")
    public String updateTraffic(@RequestBody TrafficData data) {
        this.trafficData = data;
        System.out.println("Received from ESP32: " + data.getRed() + ", " + data.getGreen() + ", " + data.getYellow());
        return "{\"status\": \"success\"}";
    }

    @GetMapping("/intervals")
    public TrafficData getIntervals() {
        return trafficData;
    }
}
