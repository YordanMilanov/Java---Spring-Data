package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AgentImportDTO {

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    private String town;

    @Email
    private String email;

}
