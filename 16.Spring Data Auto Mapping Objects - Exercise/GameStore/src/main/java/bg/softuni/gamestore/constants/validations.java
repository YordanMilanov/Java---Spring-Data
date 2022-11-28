package bg.softuni.gamestore.constants;

import bg.softuni.gamestore.domain.dtos.GameDTO;

public enum validations {
    ;
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String EMAIL_NOT_VALID_MASSAGE = "Incorrect email.";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public static final String USERNAME_OR_PASSWORD_NOT_VALID_MASSAGE = "Incorrect Username / Password!";
    public static final String PASSWORD_MISS_MATCH_MASSAGE = "Passwords are not matching";
    public static final String COMMAND_NOT_FOUND = "Command not found";

    //this validations should be done in the setters of the parameters in the constructor!g
    public static void validateGame(GameDTO game) {
        if (Character.isUpperCase(game.getTitle().charAt(0))
                && game.getTitle().length() >= 3
                && game.getTitle().length() <= 100) {
            throw new IllegalArgumentException("Not a valid game title!");
        }

        if(game.getPrice().longValue() > 0 && game.getSize() > 0.0) {
            throw new IllegalArgumentException("Price or size should be positive number!");
        }

        if(game.getTrailerId().length() == 11) {
            throw new IllegalArgumentException("Trailer ID should be exactly 11 symbols");
        }

        if(game.getTrailerId().length() > 20) {
            throw new IllegalArgumentException("Description should be more than 20 symbols");
        }

        if (game.getImageUrl().startsWith("http://") && game.getImageUrl().startsWith("https://")) {
            throw new IllegalArgumentException("Link should be starting with http/https");
        }

    }
}
