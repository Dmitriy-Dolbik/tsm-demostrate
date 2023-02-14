package ru.karod.tsm;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.util.UserUtil;
import ru.karod.tsm.util.impl.TsmUserUtilImpl;

public class TsmUserUtilImplTest
{
    private static UserUtil tsmUserUtil = new TsmUserUtilImpl();

    @Test
    void getUserFullNameForEmailTemplateTest_withFirstNameAndLastName_ShouldReturnFullName()
    {
        //Given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));

        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("Dmitrii");
        user.setLastName("Dolbik");
        user.setSubjects(subjects);
        user.setPassword("1Dfd33");

        //When
        String userFullName = tsmUserUtil.getUserFullNameForEmailTemplate(user);

        //Then
        Assertions.assertEquals("Dmitrii Dolbik", userFullName);
    }

    @Test
    void getUserFullNameForEmailTemplateTest_withOnlyFirstName_ShouldReturnFirstName()
    {
        //Given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));

        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("Dmitrii");
        user.setSubjects(subjects);
        user.setPassword("1Dfd33");

        //When
        String userFullName = tsmUserUtil.getUserFullNameForEmailTemplate(user);

        //Then
        Assertions.assertEquals("Dmitrii", userFullName);

    }

    @Test
    void getUserFullNameForEmailTemplateTest_withOnlyLastName_ShouldReturnLastName()
    {
        //Given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));

        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setLastName("Dolbik");
        user.setSubjects(subjects);
        user.setPassword("1Dfd33");

        //When
        String userFullName = tsmUserUtil.getUserFullNameForEmailTemplate(user);

        //Then
        Assertions.assertEquals("Dolbik", userFullName);
    }
    @Test
    void getUserFullNameForEmailTemplateTest_withNoName_ShouldReturnCustomer()
    {
        //Given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));

        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setSubjects(subjects);
        user.setPassword("1Dfd33");

        //When
        String userFullName = tsmUserUtil.getUserFullNameForEmailTemplate(user);

        //Then
        Assertions.assertEquals("customer", userFullName);
    }
}
