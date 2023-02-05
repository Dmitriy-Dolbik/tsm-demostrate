package ru.karod.tsm.util.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.karod.tsm.exceptions.GettingIPAddressException;
import ru.karod.tsm.exceptions.ReadingFileException;
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
        URL ipURL = null;
        String serverPublicIP = null;
        try
        {
            ipURL = new URL("http://checkip.amazonaws.com");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(ipURL.openStream()))){
                serverPublicIP = in.readLine();
            }
        }
        catch (IOException e)
        {
            throw new GettingIPAddressException(String.format(
                    "Error during getting an IP adress via http://checkip.amazonaws.com, message: [%s]", e.getMessage()));
        }
        String serverURL = "http://" + serverPublicIP + ":" + serverPort;
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
