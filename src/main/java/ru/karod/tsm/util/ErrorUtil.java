package ru.karod.tsm.util;

import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;

/**
 * The supporting class for working with error data
 */
public interface ErrorUtil
{
    /**
     * @param bindingResult
     * @return an error message to send to a client
     */
    String createErrorMessageToClient(@NotNull final BindingResult bindingResult);
}
