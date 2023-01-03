package ru.karod.tsm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.repositories.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.RollbackException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Value("${company_email}")
    private String companyEmail;
    @Value("${company_name}")
    private String companyName;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    private User getUserByPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username not found with email : " + email));
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User with id: " + userId + " cannot be found"));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void register(User user, String siteURL) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String userId = UUID.randomUUID().toString();
        user.setId(userId);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setVerified(false);

        try {
            log.info("Saving User {}", user.getEmail());
            userRepository.save(user);
        } catch (RollbackException rolExc) {
            rolExc.printStackTrace();
            log.error("Error during registration. {}", rolExc.getMessage());
            throw new InvalidRequestValuesException("The user with email " + user.getUsername() + ". Error during registration: " + rolExc.getMessage());
        }

        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL) {
        String toAddress = user.getEmail();//куда отправляем
        String fromAddress = companyEmail;//откуда отправляем
        String senderName = companyName;//название компании
        String subject = "Please verify your registration";//тема письма
        String content = "Dear customer,<br>"//шаблон письма
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "TSM project.";

        MimeMessage message = mailSender.createMimeMessage();//создаем объект сообщения для отправки
        MimeMessageHelper helper = new MimeMessageHelper(message);//помещаем в helper для задания характеристик

        try {
            helper.setFrom(fromAddress, senderName);//откуда отправляем
            helper.setTo(toAddress);//куда отправляем
            helper.setSubject(subject);//тема письма

            String fullUserName = user.getFirstName() + " " + user.getLastName();

            content = content.replace("[[name]]", fullUserName);

            String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new NotFoundException(
                        "The account can't be verify. " +
                                "It maybe already verified, or verification code is incorrect"));

        user.setVerificationCode(null);
        user.setVerified(true);
        userRepository.save(user);

        return true;
    }
}
