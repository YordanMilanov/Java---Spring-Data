package bg.softuni.modelmapper;
import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dtos.AddressDTO;
import bg.softuni.modelmapper.entities.dtos.CreateEmployeeDTO;
import bg.softuni.modelmapper.services.AddressService;
import bg.softuni.modelmapper.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {
        private final AddressService addressService;
    private final EmployeeService employeeService;

    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {



        Scanner scanner = new Scanner(System.in);
      //  createAddress(scanner);
      //  createEmployee(scanner);

        printAllEmployees();
    }

    private void printAllEmployees() {
        this.employeeService.findAll().forEach(System.out::println);
    }


    private void createEmployee(Scanner scanner) {
            String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());
       // long addressId = Long.parseLong(scanner.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO address = new AddressDTO(country, city);

        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO(firstName, null, salary, birthday, address);

        this.employeeService.create(employeeDTO);

        System.out.println(employeeDTO);
    }

    private void createAddress(Scanner scanner) {
        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO data = new AddressDTO(country, city);

        Address address = addressService.create(data);

        System.out.println(address);
    }
}
