package com.nltu.traffic_rest_controller.controller;

import com.nltu.traffic_rest_controller.entity.TrafficData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private TrafficData trafficData = new TrafficData();

    @Value("${esp32.url:http://localhost:8180}")
    private String esp32Url;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/update")
    public String updateTraffic(@RequestBody TrafficData data) {
        this.trafficData = data;
        //System.out.println("Received from client: " + data.getRed() + ", " + data.getGreen() + ", " + data.getYellow());
        String url = esp32Url + "/setTrafficData"
                + "?q1=" + data.getQ1()
                + "&s1=" + data.getS1()
                + "&q2=" + data.getQ2()
                + "&s2=" + data.getS2();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("ESP32 responded: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return "{\"status\": \"success\"}";
    }
}
