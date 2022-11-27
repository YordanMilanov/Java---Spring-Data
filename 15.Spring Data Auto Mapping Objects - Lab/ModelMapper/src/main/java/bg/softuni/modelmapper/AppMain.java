package bg.softuni.modelmapper;
import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dtos.CreateAddressDTO;
import bg.softuni.modelmapper.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {
        private final AddressService addressService;

        @Autowired
    public AppMain(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {



        Scanner scanner = new Scanner(System.in);
      //  createAddress(scanner);
        createEmployee(scanner);
    }

    private void createEmployee(Scanner scanner) {
    }

    private void createAddress(Scanner scanner) {
        String country = scanner.nextLine();
        String city = scanner.nextLine();

        CreateAddressDTO data = new CreateAddressDTO(country, city);

        Address address = addressService.create(data);

        System.out.println(address);
    }
}