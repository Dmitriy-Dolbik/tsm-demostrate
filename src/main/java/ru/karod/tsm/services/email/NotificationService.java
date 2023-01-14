package ru.karod.tsm.services.email;

import ru.karod.tsm.models.User;

/**
 * Interface for sending notifications to a user
 */
public interface NotificationService
{
    /**
     * Method for sending a notification about a verification of the user email
     */
    void sendVerificationLetterForUser(User user);
}
