package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double income;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "employee_count")
    private int employeeCount;

    @Column(nullable = false, name = "shop_area")
    private int shopArea;

    @ManyToOne
    private Town town;
}
