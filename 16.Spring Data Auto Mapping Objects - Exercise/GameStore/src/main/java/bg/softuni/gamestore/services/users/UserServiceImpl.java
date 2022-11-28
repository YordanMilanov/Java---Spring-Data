package bg.softuni.gamestore.services.users;

import bg.softuni.gamestore.domain.dtos.UserRegister;
import bg.softuni.gamestore.domain.entities.User;
import bg.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String RegisterUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];
        UserRegister userRegister = new UserRegister(email, password, confirmPassword, fullName);
        User map = this.modelMapper.map(userRegister, User.class);
        return null;
    }
}
