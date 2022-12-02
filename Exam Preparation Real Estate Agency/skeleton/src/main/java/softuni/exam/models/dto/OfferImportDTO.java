package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
public class OfferImportDTO {

    @Positive
    @XmlElement
    private Double price;

    @XmlElement
    private String publishedOn;

    @XmlElement(name = "agent")
    private nameDTO agent;


    @XmlElement(name = "apartment")
    private IdDTO apartment;

}
