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
    private static String VERIFY_URL;

    @Value("${spring.google.recaptcha.secret-key}")
    private static String SECRET_KEY;

    public boolean validateRecaptcha(String token) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("secret", SECRET_KEY);
        request.add("response", token);

        RecaptchaResponse apiResponse = restTemplate.postForObject(VERIFY_URL, request, RecaptchaResponse.class);

        return Boolean.TRUE.equals(apiResponse.success());
    }
}
