package ru.karod.tsm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }
    private User getUserByPrincipal(Principal principal){
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Username not found with email : "+email));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()->
                new UsernameNotFoundException("User cannot be found"));
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    public void createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setId(UUID.randomUUID());
        user.setPassword(encodedPassword);
        try {
            log.info("Saving User {}", user.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new InvalidRequestValuesException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }
}
