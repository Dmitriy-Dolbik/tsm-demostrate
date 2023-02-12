package ru.karod.tsm.services;

import java.security.Principal;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.models.User;

/**
 *
 */
public interface UserService
{
    User updateUser(@NotNull final UserDTO userDTO, @NotNull final Principal principal);

    User getUserById(@NotNull final String userId);

    Optional<User> findUserByEmail(@NotNull final String email);

    User register(@NotNull final User user);

    boolean verify(@NotNull final String verificationCode);
}
