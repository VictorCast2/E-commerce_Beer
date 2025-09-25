package com.application.service.http;

import com.application.presentation.dto.general.response.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("${spring.google.recaptcha.url}")
    private String verifyURL;

    @Value("${spring.google.recaptcha.secret-key}")
    private String secretKey;

    public boolean validateRecaptcha(String token) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("secret", secretKey);
        request.add("response", token);

        RecaptchaResponse apiResponse = restTemplate.postForObject(verifyURL, request, RecaptchaResponse.class);

        return Boolean.TRUE.equals(apiResponse.success());
    }
}
