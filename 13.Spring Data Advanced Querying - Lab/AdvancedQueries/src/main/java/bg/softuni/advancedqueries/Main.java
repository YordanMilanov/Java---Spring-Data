package bg.softuni.advancedqueries;

import bg.softuni.advancedqueries.entities.Shampoo;
import bg.softuni.advancedqueries.entities.Size;
import bg.softuni.advancedqueries.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

// за да се изпълнява и менажира този мейн от спринг трябва да имплементира commandLineRunner и да има анотация @Component
@Component
public class Main implements CommandLineRunner {


    private final ShampooService shampooService;

    @Autowired
    public Main(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String size = scanner.nextLine();

        for (Shampoo shampoo : this.shampooService.findByBrandAndSize("Cotton Fresh", size)) {
            System.out.println(shampoo);
        }

    }
}
