package ru.karod.tsm.services;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;

public interface CustomUserDetailsService extends UserDetailsService
{
    UserDetails loadUserByUsername(@NotNull final String email);

    User loadUserById(@NotNull final String id);
}
