package ru.karod.tsm.services.email.impl;

import java.io.UnsupportedEncodingException;
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
import ru.karod.tsm.models.EmailTemplate;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.services.email.EmailSender;
import ru.karod.tsm.services.email.EmailTemplateHandler;

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

    @Override
    public void sendEmail(@NotNull final User user, @NotNull final EmailType emailType, @NotNull final String siteURL)
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        EmailTemplate emailTemplate = emailTemplateHandler.getTemplate(emailType);

        try
        {
            helper.setFrom(companyEmail, companyName);
            helper.setTo(user.getEmail());
            helper.setSubject(emailTemplate.getSubject());

            StringBuilder fullUsername = new StringBuilder();
            if (user.getFirstName() != null)
            {
                fullUsername.append(user.getFirstName());
            }
            if (user.getLastName() != null)
            {
                fullUsername.append(user.getFirstName());
            }
            if (fullUsername.isEmpty())
            {
                fullUsername.append("customer");
            }
            String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

            String content = emailTemplate.getTemplate();
            content = content.replace("{{name}}", fullUsername.toString());
            content = content.replace("{{verification_url}}", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}