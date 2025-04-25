package ru.mihanizzm.hashcrackermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrackRequest {
    private String hash;
    private int maxLength;
}