package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CarSeedDTO {

    @Expose
    @Size(min = 2, max = 19)
    private String make;

    @Expose
    @Size(min = 2, max = 19)
    private String model;

    @Expose
    @Positive
    private Long kilometers;

    @Expose
    private String registeredOn;
}
