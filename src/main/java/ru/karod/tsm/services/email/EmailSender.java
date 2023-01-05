package ru.karod.tsm.services.email;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSender
{
    public void sendEmail (String email, Map<String, String> params, String template){

    }
}
