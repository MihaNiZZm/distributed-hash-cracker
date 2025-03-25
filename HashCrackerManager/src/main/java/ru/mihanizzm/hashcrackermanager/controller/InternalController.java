package ru.mihanizzm.hashcrackermanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mihanizzm.hashcrackermanager.dto.HashCrackResult;
import ru.mihanizzm.hashcrackermanager.service.CrackService;

@RestController
@RequestMapping("/internal/api/manager")
@RequiredArgsConstructor
public class InternalController {
    private final CrackService crackService;

    @PatchMapping("/hash/crack/request")
    public ResponseEntity<Void> updateRequest(@RequestBody HashCrackResult result) {
        crackService.updateRequest(result);
        return ResponseEntity.ok().build();
    }
}
