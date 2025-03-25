package ru.mihanizzm.hashcrackerworker.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "HashCrackTask")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HashCrackTask {
    @XmlElement(name = "RequestId")
    private String requestId;

    @XmlElement(name = "Hash")
    private String hash;

    @XmlElement(name = "Alphabet")
    private String alphabet;

    @XmlElement(name = "Length")
    private int length;

    @XmlElement(name = "PartCount")
    private int partCount;

    @XmlElement(name = "PartNumber")
    private int partNumber;
}
