package bg.softuni.advancedqueries;

import bg.softuni.advancedqueries.entities.Ingredient;
import bg.softuni.advancedqueries.entities.Shampoo;
import bg.softuni.advancedqueries.services.IngredientService;
import bg.softuni.advancedqueries.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Scanner;

// за да се изпълнява и менажира този мейн от спринг трябва да имплементира commandLineRunner и да има анотация @Component
@Component
public class Main implements CommandLineRunner {


    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Integer input =Integer.parseInt(scanner.nextLine());
        List<Shampoo> shampoos = shampooService.findAllShampoosWithIngredientsLessThan(input);
        for (Shampoo shampoo : shampoos) {
            System.out.println(shampoo);
        }
    }
}
