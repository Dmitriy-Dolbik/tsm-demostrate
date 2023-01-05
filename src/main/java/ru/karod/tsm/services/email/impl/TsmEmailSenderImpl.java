package ru.karod.tsm.services.email.impl;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.karod.tsm.services.email.EmailSender;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsmEmailSenderImpl implements EmailSender
{
    @Override
    public void sendEmail (@NotNull final String email, @NotNull final Map<String, String> params, @NotNull final String template){

    }
}
