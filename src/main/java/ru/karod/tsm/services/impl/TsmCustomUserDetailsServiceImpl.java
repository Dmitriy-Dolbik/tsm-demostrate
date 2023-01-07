package ru.karod.tsm.services.impl;

import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.repositories.UserRepository;
import ru.karod.tsm.services.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class TsmCustomUserDetailsServiceImpl implements CustomUserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NotNull final String email)
    {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException
                        ("User not found with email: " + email));
    }

    public User loadUserById(@NotNull final String id)
    {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException
                        ("User not found with id: " + id));
    }
}
