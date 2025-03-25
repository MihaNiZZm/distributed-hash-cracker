package ru.mihanizzm.hashcrackermanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mihanizzm.hashcrackermanager.dto.CrackRequestDto;
import ru.mihanizzm.hashcrackermanager.dto.CrackResponse;
import ru.mihanizzm.hashcrackermanager.dto.StatusResponse;
import ru.mihanizzm.hashcrackermanager.model.CrackRequest;
import ru.mihanizzm.hashcrackermanager.service.CrackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hash")
public class HashController {
    private final CrackService crackService;

    @PostMapping("/crack")
    public ResponseEntity<CrackResponse> crackHash(@RequestBody CrackRequestDto request) {
        String requestId = crackService.processRequest(request.hash(), request.maxLength());
        return ResponseEntity.ok(new CrackResponse(requestId));
    }

    @GetMapping("/status")
    public ResponseEntity<StatusResponse> getStatus(@RequestParam String requestId) {
        CrackRequest request = crackService.getRequest(requestId);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        StatusResponse response = new StatusResponse(
                request.getStatus().name(),
                request.getResults()
        );
        return ResponseEntity.ok(response);
    }
}