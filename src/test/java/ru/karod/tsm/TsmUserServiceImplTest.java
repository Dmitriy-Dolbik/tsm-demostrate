package ru.karod.tsm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.repositories.UserRepository;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.services.email.NotificationService;
import ru.karod.tsm.services.email.impl.TsmNotificationService;
import ru.karod.tsm.services.impl.TsmUserServiceImpl;

import static org.mockito.ArgumentMatchers.any;

public class TsmUserServiceImplTest
{
    private static UserService tsmUserServiceImpl;
    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private static NotificationService tsmNotificationService;
    private static List<Subject> subjects;
    private static User user;
    private static final String USER_ID = "ef97eb6f-3396-4b39-97f0-7eecf7510d56";
    private static final String EMAIL = "dda1519@gmail.com";

    @BeforeAll
    static void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        tsmNotificationService = Mockito.mock(TsmNotificationService.class);
        tsmUserServiceImpl = new TsmUserServiceImpl(userRepository, passwordEncoder, tsmNotificationService);


    }
    @BeforeEach
    void createInstances(){
        subjects = new ArrayList<>(List.of(
                new Subject ("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject ("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));
        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("Dmitrii");
        user.setLastName("Dolbik");
        user.setSubjects(subjects);
        user.setPassword("1Dfd33");
    }
    @Test
    void registrarTest_shouldReturnRegisteredUser(){
        //Given
        User registeredUser = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("Dmitrii");
        user.setLastName("Dolbik");
        user.setSubjects(subjects);
        user.setPassword("$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG");

        Mockito.when(passwordEncoder.encode((user.getPassword()))).thenReturn("$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG");
        Mockito.when(userRepository.save(user)).thenReturn(registeredUser);

        //When
        User userRegistered = tsmUserServiceImpl.register(user);

        //Then
        Assertions.assertEquals("dda1519@gmail.com", userRegistered.getEmail());
        Assertions.assertEquals(Role.ROLE_TEACHER, userRegistered.getRole());
        Assertions.assertEquals("Dmitrii", userRegistered.getFirstName());
        Assertions.assertEquals("Dolbik", userRegistered.getLastName());
        for (int i = 0; i < subjects.size(); i++)
        {
            Assertions.assertEquals(subjects.get(i), userRegistered.getSubjects().get(i));
        }
        Assertions.assertEquals("$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG", userRegistered.getPassword());
    }
    @Test
    void verifyTest_shouldReturnTrue(){
        //Given
        String verificationCode = "7Y2I6lKKihJlETYpendwGJAadxaTuHA5vSEmNRCNfNJVCIv2Z2J3V808CuHG5Fdn";
        user.setVerificationCode(verificationCode);
        user.setVerified(false);

       /* User verifiedUser = new User(
                "dda1519@gmail.com",
                Role.ROLE_TEACHER,
                "Dmitrii",
                "Dolbik",
                subjects,
                "$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG"
        );
        verifiedUser.setVerificationCode(null);
        verifiedUser.setVerified(true);
        Mockito.when(userRepository.save(user)).thenReturn(verifiedUser);*/

        Mockito.when(userRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(user));


        //When
        boolean result = tsmUserServiceImpl.verify(verificationCode);

        //Then
        Assertions.assertTrue(result);
    }
    @Test
    void updateTest(){
        //Given
        Principal principal = Mockito.mock(Principal.class);
        UserDTO userDTO = UserDTO.builder()
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .age((short) 18)
                .phoneNumber("123132")
                .build();

        User updatedUser = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("UpdatedFirstName");
        user.setLastName("UpdatedLastName");
        user.setSubjects(subjects);
        user.setPassword("$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG");
        user.setAge((short)18);
        user.setPassword("123132");

        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(updatedUser);

        //When
        User userUpdated = tsmUserServiceImpl.updateUser(userDTO,principal);

        //Then
        Assertions.assertEquals("UpdatedFirstName", userUpdated.getFirstName());
        Assertions.assertEquals("UpdatedLastName", userUpdated.getLastName());
        Assertions.assertEquals((short)18, userUpdated.getAge());
        Assertions.assertEquals("123132", userUpdated.getPhoneNumber());
    }
    @Test
    void getUserByIdTest_shouldReturnUserById(){
        //Given
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //When
        User userById = tsmUserServiceImpl.getUserById(USER_ID);

        //Then
        Assertions.assertEquals("dda1519@gmail.com", userById.getEmail());
        Assertions.assertEquals("Dmitrii", userById.getFirstName());
        Assertions.assertEquals("Dolbik", userById.getLastName());
        Assertions.assertEquals("1Dfd33", userById.getPassword());
    }
    @Test
    void getUserByEmailTest(){
        //Given
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));

        //When
        User userByEmail = tsmUserServiceImpl.findUserByEmail(EMAIL)
                .orElseThrow(()->new UsernameNotFoundException("User with email: " + EMAIL + " cannot be found"));

        //Then
        Assertions.assertEquals("dda1519@gmail.com", userByEmail.getEmail());
        Assertions.assertEquals("Dmitrii", userByEmail.getFirstName());
        Assertions.assertEquals("Dolbik", userByEmail.getLastName());
        Assertions.assertEquals("1Dfd33", userByEmail.getPassword());
    }



}
