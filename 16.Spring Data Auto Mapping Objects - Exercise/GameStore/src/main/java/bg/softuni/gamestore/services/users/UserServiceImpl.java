package bg.softuni.gamestore.services.users;

import bg.softuni.gamestore.domain.dtos.UserLoginDTO;
import bg.softuni.gamestore.domain.dtos.UserRegisterDTO;
import bg.softuni.gamestore.domain.entities.User;
import bg.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static bg.softuni.gamestore.constants.validations.USERNAME_OR_PASSWORD_NOT_VALID_MASSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private  User loggedInUser;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];

        UserRegisterDTO userRegisterDTO;

        try {
            userRegisterDTO = new UserRegisterDTO(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }

       final User user = this.modelMapper.map(userRegisterDTO, User.class);

       if (this.userRepository.count() == 0) {
           user.setIsAdmin(true);
       }

        boolean isUserFound = this.userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent();

        if (isUserFound) {
         //   throw new IllegalArgumentException("Email already exists!");
            return "Email already exists!";
        }

        this.userRepository.save(user);

        return userRegisterDTO.successfulRegisterFormat();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        UserLoginDTO userLogin = new UserLoginDTO(email, password);

        Optional<User> user = this.userRepository.findFirstByEmail(userLogin.getEmail());

        if (user.isPresent() &&
                this.loggedInUser == null &&
                user.get().getPassword().equals(userLogin.getPassword())) {
            this.loggedInUser = this.userRepository.findFirstByEmail(userLogin.getEmail()).get();
            return "Successfully logged " + this.loggedInUser.getFullName();
        }

        return USERNAME_OR_PASSWORD_NOT_VALID_MASSAGE;
    }

    @Override
    public String logoutUser() {
        if(this.loggedInUser == null) {
            return "Cannot log out. No user was logged in";
        }

        String  output = "User " + this.loggedInUser.getFullName() + " was successfully logged out!";

        this.loggedInUser = null;
        return output;
    }
}
