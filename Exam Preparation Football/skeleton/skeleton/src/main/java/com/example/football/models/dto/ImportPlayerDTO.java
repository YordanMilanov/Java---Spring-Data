package com.example.football.models.dto;

import com.example.football.models.entity.enums.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;


@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
public class ImportPlayerDTO {


    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    //we set the date as String and then parse it in the mapper
    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement
    private PlayerPosition position;

    @XmlElement(name = "town")
    private NameDTO town;

    @XmlElement(name = "team")
    private NameDTO team;

    @XmlElement(name = "stat")
    private StatIdDTO stat;
}
