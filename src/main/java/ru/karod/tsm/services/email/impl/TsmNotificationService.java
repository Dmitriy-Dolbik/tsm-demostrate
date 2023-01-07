package ru.karod.tsm.services.email.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.populators.TsmPopulator;
import ru.karod.tsm.services.email.EmailSender;
import ru.karod.tsm.services.email.NotificationService;

@RequiredArgsConstructor
@Component
public class TsmNotificationService implements NotificationService
{
    private final EmailSender emailSender;
    private final TsmPopulator<User> registrationEmailParametersPopulator;

    public void sendVerificationLetterForUser(User user){
        Map<String, String> parameters = registrationEmailParametersPopulator.populate(user);
        String emailOfUser = user.getEmail();
        emailSender.sendEmail(emailOfUser, parameters, EmailType.REGISTRATION);
    }


    // Методы для отправки других типо писем
    //необходимо создать invitationEmailPopulator
    /*public void sendInvitationLetterForUser(User user){
        Map<String, String> parameters = invitationEmailPopulator.populate(user);
        String emailOfUser = user.getEmail();
        emailSender.sendEmail(emailOfUser, parameters, EmailType.INVITATION_TO_A_LESSON);
    }*/
    //необходимо создать needPaymentEmailPopulator
    /*public void sendNeedPaymentLetterForUser(User user){
        Map<String, String> parameters = needPaymentEmailPopulator.populate(user);
        String emailOfUser = user.getEmail();
        emailSender.sendEmail(emailOfUser, parameters, EmailType.BALANCE);
    }*/
}
