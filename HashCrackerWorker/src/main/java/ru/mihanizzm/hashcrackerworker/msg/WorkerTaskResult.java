package ru.mihanizzm.hashcrackerworker.msg;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "WorkerTaskResult")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerTaskResult {
    @XmlElement
    private String requestId;
    @XmlElement
    private int partNumber; // По сути, чтобы различить chunk для идемпотентности
    @XmlElement(name = "result")
    private List<String> results; // найденные слова
}