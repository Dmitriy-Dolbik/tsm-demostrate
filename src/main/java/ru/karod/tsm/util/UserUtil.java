package ru.karod.tsm.util;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.karod.tsm.models.User;

@Component
public class UserUtil
{
    @Value("${site_url}")
    private String siteURL;
    @Value("${registration_postfix}")
    private String registrationPostfix;

    public String getVerifyURLForUser(User user){
        return siteURL + registrationPostfix + user.getVerificationCode();
    }

    public String getFullNameForEmailTemplate(@NotNull final User user){
        StringBuilder fullUsername = new StringBuilder();
        if (user.getFirstName() != null)
        {
            fullUsername.append(user.getFirstName());
        }
        if (user.getLastName() != null)
        {
            fullUsername
                    .append(" ")
                    .append(user.getFirstName());
        }
        if (fullUsername.isEmpty())
        {
            fullUsername.append("customer");
        }
        return fullUsername.toString();
    }
}
