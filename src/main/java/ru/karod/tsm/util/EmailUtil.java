package ru.karod.tsm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.karod.tsm.models.User;

@Component
public class EmailUtil
{
    @Value("${site_url}")
    private String siteURL;
    @Value("${registration_postfix}")
    private String registrationPostfix;

    public String getVerifyURLForUser(User user){
        String verifyURL = siteURL + registrationPostfix + user.getVerificationCode();
        return verifyURL;
    }

    public String getFullNameForEmailTemplate(User user){
        StringBuilder fullUsername = new StringBuilder();
        if (user.getFirstName() != null)
        {
            fullUsername.append(user.getFirstName());
        }
        if (user.getLastName() != null)
        {
            fullUsername.append(user.getFirstName());
        }
        if (fullUsername.isEmpty())
        {
            fullUsername.append("customer");
        }
        return fullUsername.toString();
    }
}
