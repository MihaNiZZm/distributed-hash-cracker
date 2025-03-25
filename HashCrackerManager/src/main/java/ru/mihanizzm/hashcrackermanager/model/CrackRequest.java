package ru.mihanizzm.hashcrackermanager.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Getter
@Setter
@RequiredArgsConstructor
public class CrackRequest {
    public enum Status { IN_PROGRESS, READY, ERROR }

    private final String requestId;
    private final String targetHash;
    private final int maxLength;
    private Status status = Status.IN_PROGRESS;
    private final Set<Integer> receivedParts = new CopyOnWriteArraySet<>();
    private final List<String> results = new CopyOnWriteArrayList<>();
    private final Instant created;
    private final int totalParts;

    public CrackRequest(String requestId, String targetHash, int maxLength, int totalParts) {
        this.requestId = requestId;
        this.targetHash = targetHash;
        this.maxLength = maxLength;
        this.totalParts = totalParts;
        this.created = Instant.now();
    }
}
