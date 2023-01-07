package ru.karod.tsm.services.email;

import java.util.Map;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;

public interface EmailSender
{
    void sendEmail (@NotNull final String email,
                    @NotNull final Map<String, String> params,
                    @NotNull final EmailType emailType);

}
