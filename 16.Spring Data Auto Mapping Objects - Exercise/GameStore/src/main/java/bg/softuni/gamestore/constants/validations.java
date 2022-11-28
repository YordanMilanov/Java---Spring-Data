package bg.softuni.gamestore.constants;

public enum validations {
    ;
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String EMAIL_NOT_VALID_MASSAGE = "Incorrect email.";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public static final String USERNAME_OR_PASSWORD_NOT_VALID_MASSAGE = "Incorrect Username / Password!";
    public static final String PASSWORD_MISS_MATCH_MASSAGE = "Passwords are not matching";
    public static final String COMMAND_NOT_FOUND = "Command not found";

}
