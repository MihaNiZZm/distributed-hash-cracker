package ru.mihanizzm.hashcrackermanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusResponse(
        @JsonProperty("status") String status,
        @JsonProperty("data") List<String> data
) {}
