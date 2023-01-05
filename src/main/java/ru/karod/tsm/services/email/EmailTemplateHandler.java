package ru.karod.tsm.services.email;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.enums.EmailType;

public interface EmailTemplateHandler
{
    String getTemplate(@NotNull final EmailType typeOfEmail);
}
