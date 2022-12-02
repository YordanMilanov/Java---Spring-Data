package softuni.exam.models.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntity{

    @Column
    private String model;

    @Column
    private String make;

    @Column
    private Long kilometers;

    @Column(name = "registered_on")
    private LocalDate registeredOn;

}
