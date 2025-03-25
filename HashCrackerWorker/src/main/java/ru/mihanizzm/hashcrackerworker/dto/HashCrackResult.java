package ru.mihanizzm.hashcrackerworker.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "HashCrackResult")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HashCrackResult {
    @XmlElement(name = "RequestId")
    private String requestId;

    @XmlElement(name = "PartNumber")
    private int partNumber;

    @XmlElement(name = "Data")
    private List<String> data;
}
