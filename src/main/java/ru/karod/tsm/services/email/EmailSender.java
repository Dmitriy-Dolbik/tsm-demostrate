package ru.karod.tsm.services.email;

import java.util.Map;

import javax.validation.constraints.NotNull;

public interface EmailSender
{
    void sendEmail (@NotNull final String email, @NotNull final Map<String, String> params, @NotNull final String template);
}
