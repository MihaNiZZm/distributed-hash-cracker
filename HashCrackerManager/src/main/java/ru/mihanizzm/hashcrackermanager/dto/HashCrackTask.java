package ru.mihanizzm.hashcrackermanager.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "HashCrackTask")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class HashCrackTask {
    @XmlElement(name = "RequestId")
    private String requestId;

    @XmlElement(name = "Hash")
    private String hash;

    @XmlElement(name = "Alphabet")
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

    @XmlElement(name = "Length")
    private int length;

    @XmlElement(name = "PartCount")
    private int partCount;

    @XmlElement(name = "PartNumber")
    private int partNumber;
}
