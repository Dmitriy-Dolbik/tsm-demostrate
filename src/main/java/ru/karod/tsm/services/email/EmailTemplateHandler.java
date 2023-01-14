package ru.karod.tsm.services.email;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.enums.EmailType;

/**
 * The interface for receiving the html template of the user's email
 */
public interface EmailTemplateHandler
{
    /**
     * @param typeOfEmail email type
     * @return the html template for sending the appropriate email for the user
     */
    EmailTemplate getTemplate(@NotNull final EmailType typeOfEmail);
}
