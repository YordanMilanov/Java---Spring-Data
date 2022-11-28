package bg.softuni.gamestore.domain.dtos;

import java.util.regex.Pattern;

import static bg.softuni.gamestore.constants.validations.*;


public class UserRegister {
    //RegisterUser|<email>|<password>|<confirmPassword>|<fullName>

    private String email;

    private String password;

    private String confirmPassword;

    private String fullName;

    public UserRegister(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void validate() throws IllegalArgumentException {
       final boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, this.email);

        if (!isEmailValid) {
            throw new IllegalArgumentException(EMAIL_NOT_VALID_MASSAGE);
        }

        final boolean isPasswordValid = Pattern.matches(PASSWORD_PATTERN, this.password);
        if (!isPasswordValid){
            throw new IllegalArgumentException(USERNAME_OR_PASSWORD_NOT_VALID_MASSAGE);
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(PASSWORD_MISS_MATCH_MASSAGE);
        }
    }

    public String successfulRegisterFormat() {
        return this.fullName + " was registered!";
    }
}
