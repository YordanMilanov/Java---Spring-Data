package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private Mechanic mechanic;

    @ManyToOne
    private Part part;

    @ManyToOne
    private Car car;
}
