package softuni.exam.models.dto;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
public class ImportTaskRootDTO {

    @XmlElement(name = "task")
    List<ImportTaskDTO> tasks;
}
