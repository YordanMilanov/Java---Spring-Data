package softuni.exam.models.dto;

import lombok.Getter;
import softuni.exam.models.entity.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
public class OfferImportRootDTO {

    @XmlElement(name = "offer")
    List<OfferImportDTO> offers;
}
