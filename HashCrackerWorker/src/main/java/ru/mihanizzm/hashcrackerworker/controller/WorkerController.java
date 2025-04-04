package ru.mihanizzm.hashcrackerworker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mihanizzm.hashcrackerworker.dto.HashCrackTask;
import ru.mihanizzm.hashcrackerworker.service.HashCracker;

@RestController
@RequestMapping("/internal/api/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final HashCracker hashCracker;

    @PostMapping(value = "/hash/crack/task", consumes = MediaType.APPLICATION_XML_VALUE)
    public void handleTask(@RequestBody HashCrackTask task) {
        hashCracker.processTask(task);
    }
}
