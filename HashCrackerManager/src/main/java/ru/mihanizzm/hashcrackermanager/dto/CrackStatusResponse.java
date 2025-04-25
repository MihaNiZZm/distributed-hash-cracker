package ru.mihanizzm.hashcrackermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrackStatusResponse {
    private String status; // IN_PROGRESS, READY, ERROR
    private List<String> data; // null если ещё не просчитано
}
