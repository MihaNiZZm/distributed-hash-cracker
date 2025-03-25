package ru.mihanizzm.hashcrackermanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrackResponse(
        @JsonProperty("requestId") String requestId
) {}
