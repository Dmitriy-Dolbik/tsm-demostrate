package ru.karod.tsm.util.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.karod.tsm.models.User;
import ru.karod.tsm.util.UserUtil;

/**
 * @inheritance {@link UserUtil}
 */
@Component
public class TsmUserUtilImpl implements UserUtil
{
    @Value("${server.port}")
    private String serverPort;

    @Value("${registration_postfix}")
    private String registrationPostfix;

    @Override
    public String getVerifyURLForUser(@NotNull final User user){

        String hostname = null;
        try
        {
            hostname = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        String serverURL = "http://" + hostname + ":" + serverPort;
        return serverURL + registrationPostfix + user.getVerificationCode();
    }

    @Override
    public String getFullNameForEmailTemplate(@NotNull final User user){
        StringBuilder fullUsername = new StringBuilder();
        if (user.getFirstName() != null)
        {
            fullUsername.append(user.getFirstName());
        }
        if (user.getLastName() != null)
        {
            fullUsername
                    .append(" ")
                    .append(user.getLastName());
        }
        if (fullUsername.length() == 0)
        {
            fullUsername.append("customer");
        }
        return fullUsername.toString();
    }
}
