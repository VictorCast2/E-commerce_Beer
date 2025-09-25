package com.application.presentation.dto.general.response;

public record RecaptchaResponse(
        Boolean success,
        String challenge_ts,
        String hostname,
        Double score,
        String action
) {
}
