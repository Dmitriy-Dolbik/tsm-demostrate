package ru.karod.tsm.services.email.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.karod.tsm.exceptions.EmailSendingException;
import ru.karod.tsm.exceptions.ReadingFileException;
import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.services.email.EmailSender;
import ru.karod.tsm.services.email.EmailTemplateHandler;

/**
 * @inheritance {@link EmailSender}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TsmEmailSenderImpl implements EmailSender
{
    private final EmailTemplateHandler emailTemplateHandler;
    private final JavaMailSender mailSender;
    @Value("${company_email}")
    private String companyEmail;
    @Value("${company_name}")
    private String companyName;
    @Value("${html_templates_for_email_storage}")
    private String filePath;


    @Override
    public void sendEmail(@NotNull final String userEmail,
                          @NotNull final Map<String, String> params,
                          @NotNull final EmailType emailType)
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        EmailTemplate emailTemplate = emailTemplateHandler.getTemplate(emailType);

        try
        {
            helper.setFrom(companyEmail, companyName);
            helper.setTo(userEmail);
            helper.setSubject(emailTemplate.getSubject());

            String templateServerFileName = emailTemplate.getTemplateServerFileName();
            String htmlTemplate = getHtmlTemplateFromServer(templateServerFileName);

            String contentToSent = replacePlaceholders(htmlTemplate, params);

            helper.setText(contentToSent, true);

            mailSender.send(message);
        }
        catch (MessagingException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new EmailSendingException(String.format("Error during send [%s] email, message: [%s]", emailType, e.getMessage()));

        }
    }

    private String getHtmlTemplateFromServer(String serverFileName)
    {
        try
        {
            return Files.readString(Paths.get(filePath + serverFileName));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ReadingFileException(String.format("Error during reading html template, message: [%s]", e.getMessage()));
        }
    }

    private String replacePlaceholders(String htmlTemplate, Map<String, String> params)
    {
        for (Map.Entry<String, String> entry : params.entrySet()){
            String placeholder = entry.getKey();
            String value = entry.getValue();
            htmlTemplate = htmlTemplate.replace(placeholder, value);
        }
        return htmlTemplate;
    }


}