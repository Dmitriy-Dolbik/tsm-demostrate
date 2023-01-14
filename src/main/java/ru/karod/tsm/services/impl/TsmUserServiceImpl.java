package ru.karod.tsm.services.impl;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.RollbackException;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.repositories.UserRepository;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.services.email.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsmUserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService tsmNotificationService;

    @Override
    public User updateUser(@NotNull final UserDTO userDTO, @NotNull final Principal principal)
    {
        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    private User getUserByPrincipal(@NotNull final Principal principal)
    {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username not found with email : " + email));
    }

    @Override
    public User getUserById(@NotNull final String userId)
    {
        return userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User with id: " + userId + " cannot be found"));
    }

    @Override
    public Optional<User> findUserByEmail(@NotNull final String email)
    {

        return userRepository.findUserByEmail(email);
    }

    @Override
    public void register(@NotNull final User user, @NotNull final String siteURL)
    {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String userId = UUID.randomUUID().toString();
        user.setId(userId);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setVerified(false);

        try
        {
            log.info("Saving User {}", user.getEmail());
            userRepository.save(user);
        }
        catch (RollbackException rolExc)
        {
            rolExc.printStackTrace();
            log.error("Error during registration. {}", rolExc.getMessage());
            throw new InvalidRequestValuesException("The user with email " + user.getUsername() + ". Error during registration: " + rolExc.getMessage());
        }
        tsmNotificationService.sendVerificationLetterForUser(user);
    }

    @Override
    public boolean verify(@NotNull final String verificationCode)
    {
        User user = userRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new NotFoundException(
                        "The account can't be verify. " +
                                "It maybe already verified, or verification code is incorrect"));

        user.setVerificationCode(null);
        user.setVerified(true);
        userRepository.save(user);

        return true;
    }
}
