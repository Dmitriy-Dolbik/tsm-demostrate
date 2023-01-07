package ru.karod.tsm.services.email;

import ru.karod.tsm.models.User;

public interface NotificationService
{
    void sendVerificationLetterForUser(User user);
}
