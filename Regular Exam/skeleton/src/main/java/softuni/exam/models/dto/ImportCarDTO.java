package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.CarType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportCarDTO {

    @XmlElement
    @Size(min = 2, max = 30)
    private String carMake;

    @XmlElement
    @Size(min = 2, max = 30)
    private String carModel;

    @XmlElement
    @Positive
    private int year;

    @XmlElement
    @Size(min = 2, max = 30)
    private String plateNumber;

    @XmlElement
    @Positive
    private int kilometers;

    @XmlElement
    @Min(value = 1)
    private double engine;

    @XmlElement
    @Enumerated(value = EnumType.STRING)
    private CarType carType;
}
