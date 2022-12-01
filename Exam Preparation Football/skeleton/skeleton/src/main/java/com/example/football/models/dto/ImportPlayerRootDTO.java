package com.example.football.models.dto;

import com.example.football.models.entity.Player;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
public class ImportPlayerRootDTO {

    @XmlElement(name = "player")
    List<ImportPlayerDTO> players;
}
