package ru.mihanizzm.hashcrackermanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrackRequestDto(
        @JsonProperty("hash") String hash,
        @JsonProperty("maxLength") int maxLength
) {}
