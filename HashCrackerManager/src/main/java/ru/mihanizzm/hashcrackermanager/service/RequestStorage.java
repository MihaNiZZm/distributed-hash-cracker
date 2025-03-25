package ru.mihanizzm.hashcrackermanager.service;

import org.springframework.stereotype.Component;
import ru.mihanizzm.hashcrackermanager.model.CrackRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestStorage {
    private final Map<String, CrackRequest> requests = new ConcurrentHashMap<>();

    public void addRequest(CrackRequest request) {
        requests.put(request.getRequestId(), request);
    }

    public CrackRequest getRequest(String requestId) {
        return requests.get(requestId);
    }

    public Iterable<CrackRequest> getAllRequests() {
        return requests.values();
    }
}
