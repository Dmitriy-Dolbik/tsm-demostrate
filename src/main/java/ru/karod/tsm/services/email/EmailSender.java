package ru.karod.tsm.services.email;

import java.util.Map;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
/**
 * The class for collecting parameters which will be used to replace placeholders of the html template for
 * email with type {@link EmailType #REGISTRATION)}
 */
public interface EmailSender
{
    /**
     * The method for sending an email to the user
     * @param email user email
     * @param params parameters required to replace html template placeholders
     * @param emailType email type
     */
    void sendEmail (@NotNull final String email,
                    @NotNull final Map<String, String> params,
                    @NotNull final EmailType emailType);

}
