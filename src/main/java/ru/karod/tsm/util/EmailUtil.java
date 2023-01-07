package ru.karod.tsm.util;

import org.springframework.beans.factory.annotation.Value;

import ru.karod.tsm.models.User;

public class EmailUtil
{
    @Value("${site_url}")
    private static String SITE_URL;
    @Value("${registration_postfix}")
    private static String REGISTRATION_POSTFIX;

    public static String getVerifyURLForUser(User user){
        String verifyURL = SITE_URL + REGISTRATION_POSTFIX + user.getVerificationCode();
        return verifyURL;
    }

    public static String getFullNameForEmailTemplate(User user){
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
