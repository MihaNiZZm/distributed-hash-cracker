package ru.mihanizzm.hashcrackermanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "crack_tasks")
public class CrackTask {
    @Id
    private String requestId;
    private String hash;
    private int maxLength;
    private String alphabet;
    private int workerCount;
    @Builder.Default
    private List<CrackSubTask> subTasks = new ArrayList<>();
    @Builder.Default
    private Set<String> results = new HashSet<>();
    private CrackStatus status; // IN_PROGRESS, READY, ERROR
    private long createdAt;
}
