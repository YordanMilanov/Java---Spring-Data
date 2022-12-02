package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.ApartmentType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
public class ApartmentImportDTO {

    @XmlElement
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;

    @XmlElement
    @Min(value = 40)
    private Double area;

    @XmlElement
    private String town;
}
