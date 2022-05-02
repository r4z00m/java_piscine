package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    public static final String USER_WITH_THE_NAME_IS_ALREADY_EXIST = "User with the name is already exist!\n";
    public static final String SUCCESSFUL = "Successful!\n";
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Autowired
    private void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String saveNewUser(String userName, String password) {
        Optional<User> user = usersRepository.findByName(userName);

        if (user.isPresent()) {
            return USER_WITH_THE_NAME_IS_ALREADY_EXIST;
        }

        usersRepository.save(new User(userName, passwordEncoder.encode(password)));

        return SUCCESSFUL;
    }

    @Override
    public boolean validateUser(String userName, String password) {
        Optional<User> user = usersRepository.findByName(userName);
        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
    }
}
