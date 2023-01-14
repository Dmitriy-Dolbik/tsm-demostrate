package ru.karod.tsm.util.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.util.UserUtil;

/**
 * The supporting class for working with user data
 */
@Component
public class TsmUserUtilImpl implements UserUtil
{
    @Value("${site_url}")
    private String siteURL;
    @Value("${registration_postfix}")
    private String registrationPostfix;

    /**
     * @param user
     * @return verification URL that is used to send a confirmation email to a user and verify his email.
     */
    @Override
    public String getVerifyURLForUser(@NotNull final User user){
        return siteURL + registrationPostfix + user.getVerificationCode();
    }
    /**
     * @param user
     * @return map with parameters for email with type {@link EmailType#REGISTRATION},
     * where keys contain verification_url and full name of user.
     * If user's first and last name are null then the full name is replaced with "customer"
     */
    @Override
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
                    .append(user.getLastName());
        }
        if (fullUsername.isEmpty())
        {
            fullUsername.append("customer");
        }
        return fullUsername.toString();
    }
}
