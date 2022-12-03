package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.CarType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column(nullable = false)
    @Enumerated
    private CarType cartype;

    @Column(nullable = false, name = "car_make")
    private String carMake;

    @Column(nullable = false, name = "car_model")
    private String carModel;

    @Column
    private int year;

    @Column(nullable = false, unique = true, name = "plate_number")
    private String plateNumber;

    @Column
    private int kilometers;

    @Column
    private double engine;
}
