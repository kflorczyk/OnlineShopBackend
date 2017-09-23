package pl.kflorczyk.onlineshopbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kflorczyk.onlineshopbackend.exceptions.*;
import pl.kflorczyk.onlineshopbackend.model.User;
import pl.kflorczyk.onlineshopbackend.repository.UserRepository;
import pl.kflorczyk.onlineshopbackend.validators.EmailValidator;
import pl.kflorczyk.onlineshopbackend.validators.PasswordValidator;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

//    public UserService(@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User registerUser(String email, String password) throws InvalidEmailException, EmailAlreadyExistsException, InvalidPasswordException {
        if(userExists(email)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if(!new EmailValidator().validate(email)) {
            throw new InvalidEmailException("Invalid email");
        }

        if(!new PasswordValidator().validate(password)) {
            throw new InvalidPasswordException("Invalid password");
        }

        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        return user;
    }

    public User loginUser(String email, String password) throws UserNotFoundException, PasswordNotMatchException {
        User user = getUser(email);

        if(user == null) {
            throw new UserNotFoundException("There is no account with given email");
        }

        if(password == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException("Incorrect password");
        }

        return user;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
