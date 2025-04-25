package ru.mihanizzm.hashcrackermanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrackSubTask {
    private int partNumber;
    private boolean sentToQueue; // успешно ли была отправлена в очередь
    private boolean completed;
    @Builder.Default
    private Set<String> results = new HashSet<>();
}
