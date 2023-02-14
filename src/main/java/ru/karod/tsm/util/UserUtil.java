package ru.karod.tsm.util;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;

/**
 * The supporting interface for working with user data
 */
public interface UserUtil
{
    /**
     * @param user
     * @return verification URL that is used to send a confirmation email to a user and verify his email.
     */
    String getVerifyURLForUser(@NotNull final User user);
    /**
     * @param user
     * @return map with parameters for email with type {@link EmailType#REGISTRATION},
     * where keys contain verification_url and full name of user.
     * If user's first and last name are null then the full name is replaced with "customer"
     */
    String getUserFullNameForEmailTemplate(@NotNull final User user);

}
