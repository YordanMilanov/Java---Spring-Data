package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "towns")
@Getter
@Setter
@NoArgsConstructor
public class Town extends BaseEntity{

    @Column(unique = true, nullable = false, name = "town_name")
    private String townName;

    @Column(nullable = false)
    private Long population;
}
