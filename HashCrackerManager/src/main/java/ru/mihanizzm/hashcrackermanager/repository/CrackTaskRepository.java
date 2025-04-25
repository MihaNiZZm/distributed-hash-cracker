package ru.mihanizzm.hashcrackermanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.mihanizzm.hashcrackermanager.model.CrackStatus;
import ru.mihanizzm.hashcrackermanager.model.CrackTask;

import java.util.List;

@Repository
public interface CrackTaskRepository extends MongoRepository<CrackTask, String> {
    List<CrackTask> findAllByStatus(CrackStatus status);
}
