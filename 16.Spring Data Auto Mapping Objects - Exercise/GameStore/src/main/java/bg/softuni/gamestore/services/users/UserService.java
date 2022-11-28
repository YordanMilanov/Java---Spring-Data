package bg.softuni.gamestore.services.users;

public interface UserService {
    String registerUser(String[] args);
    String loginUser(String[] args);

    String logoutUser();
}
