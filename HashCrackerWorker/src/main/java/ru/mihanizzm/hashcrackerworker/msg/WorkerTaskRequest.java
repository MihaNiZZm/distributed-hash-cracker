package ru.mihanizzm.hashcrackerworker.msg;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "WorkerTaskRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerTaskRequest {
    @XmlElement
    private String requestId;
    @XmlElement
    private String hash;
    @XmlElement
    private String alphabet;
    @XmlElement
    private int maxLength;
    @XmlElement
    private int partNumber; // номер куска
    @XmlElement
    private int partCount;  // сколько кусков по задаче всего
}
