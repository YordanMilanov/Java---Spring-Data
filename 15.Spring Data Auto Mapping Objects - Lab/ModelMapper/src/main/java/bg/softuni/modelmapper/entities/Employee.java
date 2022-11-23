package bg.softuni.modelmapper.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthDay;
    private Adress adress;
}
