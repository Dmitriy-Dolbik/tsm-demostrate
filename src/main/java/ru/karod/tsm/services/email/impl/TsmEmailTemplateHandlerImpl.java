package ru.karod.tsm.services.email.impl;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.repositories.EmailTemplateRepository;
import ru.karod.tsm.services.email.EmailTemplateHandler;

@Service
@RequiredArgsConstructor
@Slf4j
public class TsmEmailTemplateHandlerImpl implements EmailTemplateHandler
{
    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplate getTemplate(@NotNull final EmailType emailType)
    {
        EmailTemplate emailTemplate = emailTemplateRepository.findByEmailType(emailType).orElseThrow(
                () -> new NotFoundException("Email template with emailType " + emailType +" cannot be found")
        );
        return emailTemplate;
    }
}
