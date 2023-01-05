package ru.karod.tsm.services.email.impl;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.services.email.EmailTemplateHandler;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsmEmailTemplateHandlerImpl implements EmailTemplateHandler
{
    @Override
    public String getTemplate(@NotNull final EmailType typeOfEmail){

        return;
    }
}
