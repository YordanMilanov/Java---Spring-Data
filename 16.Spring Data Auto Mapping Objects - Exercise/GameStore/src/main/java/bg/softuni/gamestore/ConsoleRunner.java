package bg.softuni.gamestore;

import bg.softuni.gamestore.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static bg.softuni.gamestore.constants.Commands.REGISTER_USER;
import static bg.softuni.gamestore.constants.validations.COMMAND_NOT_FOUND;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        final String[] input = scanner.nextLine().split("\\|");
        final String command = input[0];

        switch (command) {
           case REGISTER_USER -> userService.RegisterUser(input);
        }
    }
}
