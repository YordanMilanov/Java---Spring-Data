package bg.softuni.modelmapper.entities.dtos;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class CreateEmployeeDTO {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private AddressDTO address;

    public CreateEmployeeDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDTO address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public String toString() {
        return "CreateEmployeeDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                ", address=" + address.getCity() + " " + address.getCountry() +
                '}';
    }
}
