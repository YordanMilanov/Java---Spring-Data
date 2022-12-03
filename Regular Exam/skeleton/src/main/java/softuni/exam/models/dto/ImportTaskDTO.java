package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;

@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
public class ImportTaskDTO {

    @XmlElement(name = "date")
    private String date;

    @Positive
    @XmlElement(name = "price")
    private double price;

    @XmlElement(name = "mechanic")
    private NameDTO mechanic;

    @XmlElement(name = "part")
    private IdDTO part;

    @XmlElement(name = "car")
    private IdDTO car;

}
