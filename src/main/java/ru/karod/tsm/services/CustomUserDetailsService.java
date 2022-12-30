package ru.karod.tsm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.repositories.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new NotFoundException
                        ("User not found with email: " + email));
    }
    public User loadUserById(UUID id){
        return userRepository.findUserById(id)
                .orElseThrow(()->new NotFoundException
                        ("User not found with id: " + id));
    }
}
