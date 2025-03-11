package com.trendy.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${portone.rest-api-key}")
    private String apiKey;

    @Value("${portone.rest-api-secret}")
    private String apiSecret;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String requestPayment(String paymentType, Long orderId, String userId, int amount) {
        String url = "https://api.portone.io/v1/payments/initialize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderId);
        params.put("user_id", userId);
        params.put("amount", amount);
        params.put("payment_type", paymentType);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                return (String) body.get("redirect_url");
            } else {
                throw new RuntimeException("Payment initialization failed.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during payment processing: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> verifyPayment(String impUid) {
        String url = "https://api.portone.io/v1/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Payment verification failed.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during payment verification: " + e.getMessage(), e);
        }
    }
}