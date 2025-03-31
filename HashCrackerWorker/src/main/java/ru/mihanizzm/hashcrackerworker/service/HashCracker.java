package ru.mihanizzm.hashcrackerworker.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mihanizzm.hashcrackerworker.dto.HashCrackResult;
import ru.mihanizzm.hashcrackerworker.dto.HashCrackTask;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashCracker {
    private final RestTemplate restTemplate;

    public void processTask(HashCrackTask task) {
        new Thread(() -> {
            GenerationParams params = new GenerationParams(
                    task.getAlphabet(),
                    task.getLength(),
                    task.getPartCount(),
                    task.getPartNumber()
            );

            List<String> foundWords = findMatchingHashes(task.getHash(), params);
            sendResult(new HashCheckResult(task.getRequestId(), task.getPartNumber(), foundWords));
        }).start();
    }

    private List<String> findMatchingHashes(String targetHash, GenerationParams params) {
        List<String> results = new ArrayList<>();
        long total = (long) Math.pow(params.alphabet().length(), params.length());
        long start = params.partNumber() * total / params.partCount();
        long end = (params.partNumber() + 1) * total / params.partCount() - 1;

        for (long i = start; i <= end; i++) {
            String word = generateWord(params.alphabet(), params.length(), i);
            if (DigestUtils.md5Hex(word).equals(targetHash)) {
                results.add(word);
            }
        }
        return results;
    }

    private String generateWord(String alphabet, int length, long index) {
        char[] chars = new char[length];
        int base = alphabet.length();

        for (int i = length - 1; i >= 0; i--) {
            chars[i] = alphabet.charAt((int) (index % base));
            index /= base;
        }
        return new String(chars);
    }

    private void sendResult(HashCheckResult result) {
        HashCrackResult response = new HashCrackResult();
        response.setRequestId(result.requestId());
        response.setPartNumber(result.partNumber());
        response.setData(result.foundWords());

        restTemplate.patchForObject(
                "http://manager/internal/api/manager/hash/crack/request",
                response,
                Void.class
        );
    }

    public record GenerationParams(
            String alphabet,
            int length,
            int partCount,
            int partNumber
    ) {}

    public record HashCheckResult(
            String requestId,
            int partNumber,
            List<String> foundWords
    ) {}
}
