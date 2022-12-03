package exam.model.dtos;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "towns")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
public class ImportTownRootDTO {

    @XmlElement(name = "town")
    List<ImportTownDTO> towns;
}
