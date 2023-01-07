package ru.karod.tsm.services.email;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.enums.EmailType;

public interface EmailTemplateHandler
{
    EmailTemplate getTemplate(@NotNull final EmailType typeOfEmail);
}
