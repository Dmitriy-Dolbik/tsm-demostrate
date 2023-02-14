package ru.karod.tsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.populators.impl.RegistrationEmailParametersPopulator;
import ru.karod.tsm.util.UserUtil;
import ru.karod.tsm.util.impl.TsmUserUtilImpl;

public class RegistrationEmailParametersPopulatorTest
{
    private static RegistrationEmailParametersPopulator populator;
    private static UserUtil tsmUserUtilImpl;
    @Value("${server.port}")
    private String serverPort;
    @Value("${registration_postfix}")
    private String registrationPostfix;
    @Value("${site_url}")
    private String siteURL;

    @BeforeAll
    static void setup(){
        tsmUserUtilImpl = new TsmUserUtilImpl();
        populator = new RegistrationEmailParametersPopulator(tsmUserUtilImpl);
    }

    @Test
    void populateTest(){
        //Giver
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("ef97eb6f-3396-4b39-97f0-7eecf7510d56", Language.GERMAN),
                new Subject ("dd1783ea-4d14-4a56-b279-5abee842ae19", Language.FRENCH)
        ));
        User user = new User();
        user.setEmail("dda1519@gmail.com");
        user.setRole(Role.ROLE_TEACHER);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setSubjects(subjects);
        user.setPassword("$2a$10$VpD10C32jsG/eZoFIwXxMOZZUHrChPonBGB1ImFWXk6kfcNzXeALG");
        user.setAge((short)18);
        user.setPassword("123132");
        user.setVerificationCode("12313kjbksdjglg1k2t");

        String verifyURLForUser =  siteURL + ":" + serverPort + registrationPostfix + user.getVerificationCode();

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("${fullName}", "FirstName LastName");
        expectedParams.put("${verification_url}", verifyURLForUser);

        //When
        Map<String,String> params = populator.populate(user);

        //Then
        Set<Map.Entry<String, String>> expectedEntries = expectedParams.entrySet();
        Set<Map.Entry<String, String>> resultEntries = params.entrySet();

        for (Map.Entry<String, String> expectedEntry : expectedEntries){
            Assertions.assertTrue(resultEntries.contains(expectedEntry));
        }

    }
}
