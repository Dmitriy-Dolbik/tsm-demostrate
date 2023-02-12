package ru.karod.tsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.aspectj.weaver.patterns.IVerificationRequired;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.bytebuddy.utility.RandomString;
import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.repositories.UserRepository;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.services.email.NotificationService;
import ru.karod.tsm.services.email.impl.TsmNotificationService;
import ru.karod.tsm.services.impl.TsmUserServiceImpl;

public class TsmUserServiceImplTest
{
    private static UserService tsmUserServiceImpl;
    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private static NotificationService tsmNotificationService;
    private static List<Subject> subjects;
    private static User user;



    @BeforeAll
    static void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        tsmNotificationService = Mockito.mock(TsmNotificationService.class);
        tsmUserServiceImpl = new TsmUserServiceImpl(userRepository, passwordEncoder, tsmNotificationService);

        subjects = new ArrayList<>(List.of(
                new Subject ("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject ("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));
        user = new User(
                "dda1519@gmail.com",
                Role.ROLE_TEACHER,
                "Dmitrii",
                "Dolbik",
                subjects,
                "1Dfd33");
    }
    @Test
    void registrarTest(){
        //Given
        User registeredUser = new User(
            "dda1519@gmail.com",
                Role.ROLE_TEACHER,
                "Dmitrii",
                "Dolbik",
                subjects,
                "$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG"
        );
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
    void verifyTest(){
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

}
