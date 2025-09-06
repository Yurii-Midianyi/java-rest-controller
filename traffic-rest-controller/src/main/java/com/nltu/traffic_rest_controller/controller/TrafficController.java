package com.nltu.traffic_rest_controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nltu.traffic_rest_controller.entity.TrafficData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${esp32.url:http://localhost:8180}")
    private String esp32Url;

    @Value("${app.kafka.topic}")
    private String kafkaTopic;

    public TrafficController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/update")
    public String updateTraffic(@RequestBody TrafficData data) {
        try {
            // Перетворюємо в JSON
            String jsonMessage = new ObjectMapper().writeValueAsString(data);

            // Відправляємо у Kafka
            kafkaTemplate.send(kafkaTopic, jsonMessage);
            System.out.println("Sent to Kafka: " + jsonMessage);

            // Відправляємо на ESP32
            String url = esp32Url + "/setTrafficData"
                    + "?q1=" + data.getQ1()
                    + "&s1=" + data.getS1()
                    + "&q2=" + data.getQ2()
                    + "&s2=" + data.getS2();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("ESP32 responded: " + response.getBody());

            return "{\"status\":\"success\"}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }
}
