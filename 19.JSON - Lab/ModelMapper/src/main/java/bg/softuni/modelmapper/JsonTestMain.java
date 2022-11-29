package bg.softuni.modelmapper;

import bg.softuni.modelmapper.entities.dtos.AddressDTO;
import bg.softuni.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class JsonTestMain implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                //.setPrettyPrinting()
                .create();

        AddressDTO addressDTO = new AddressDTO("Bulgaria", "Varna");
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO("drago", "chaq", BigDecimal.TEN, LocalDate.now(), addressDTO);

        String json = gson.toJson(createEmployeeDTO);
        System.out.println(json);

        String input = scanner.nextLine();

        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);

        System.out.println();
    }
}
