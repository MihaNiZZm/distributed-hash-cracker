package ru.mihanizzm.hashcrackermanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mihanizzm.hashcrackermanager.dto.CrackRequest;
import ru.mihanizzm.hashcrackermanager.dto.CrackResponse;
import ru.mihanizzm.hashcrackermanager.dto.CrackStatusResponse;
import ru.mihanizzm.hashcrackermanager.service.CrackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hash")
public class CrackHashController {

    private final CrackService crackService;

    // Создать задачу (POST /api/hash/crack)
    @PostMapping("/crack")
    public CrackResponse startCrack(@RequestBody CrackRequest req) {
        return crackService.startCrack(req);
    }

    // Узнать статус задачи (GET /api/hash/status?requestId=...)
    @GetMapping("/status")
    public CrackStatusResponse status(@RequestParam String requestId) {
        return crackService.getStatus(requestId);
    }
}
