package ru.mihanizzm.hashcrackerworker.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.mihanizzm.hashcrackerworker.config.RabbitMQConfig;
import ru.mihanizzm.hashcrackerworker.msg.WorkerTaskRequest;
import ru.mihanizzm.hashcrackerworker.msg.WorkerTaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrackWorkerService {
    private final RabbitTemplate rabbitTemplate;

    public void processTask(WorkerTaskRequest req) {
        List<String> found = crackTask(req);

        WorkerTaskResult result = WorkerTaskResult.builder()
                .requestId(req.getRequestId())
                .partNumber(req.getPartNumber())
                .results(found)
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.MANAGER_RESULTS_EXCHANGE,
                RabbitMQConfig.MANAGER_RESULTS_ROUTING_KEY,
                result
        );
    }

    private List<String> crackTask(WorkerTaskRequest req) {
        if (req.getPartCount() == 0) {
            return List.of();
        }
        List<String> alphabetList = req.getAlphabet().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());

        List<String> found = new ArrayList<>();
        String targetMd5 = req.getHash();

        long totalVariants = 0;
        for (int len = 1; len <= req.getMaxLength(); len++) {
            totalVariants += Math.pow(alphabetList.size(), len);
        }

        long perPart = totalVariants / req.getPartCount();
        long startIdx = perPart * req.getPartNumber();
        long endIdx = (req.getPartNumber() == req.getPartCount() - 1) ? totalVariants : perPart * (req.getPartNumber() + 1);

        long currentIdx = 0;
        outer:
        for (int len = 1; len <= req.getMaxLength(); len++) {
            IGenerator<List<String>> generated = Generator.permutation(alphabetList).withRepetitions(len);
            for (List<String> wordList : generated) {
                if (currentIdx >= endIdx) {
                    break outer;
                }
                if (currentIdx >= startIdx) {
                    String word = String.join("", wordList);
                    String md5 = DigestUtils.md5Hex(word);
                    if (md5.equalsIgnoreCase(targetMd5)) {
                        found.add(word);
                    }
                }
                currentIdx++;
            }
        }
        return found;
    }
}
