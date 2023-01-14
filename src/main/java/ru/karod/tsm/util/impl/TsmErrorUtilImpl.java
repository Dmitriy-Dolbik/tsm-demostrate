package ru.karod.tsm.util.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.util.ErrorUtil;

/**
 * The supporting class for working with error data
 */
@Component
public class TsmErrorUtilImpl implements ErrorUtil
{
    /**
     * @param bindingResult
     * @return an error message to send to a client
     */
    @Override
    public String createErrorMessageToClient(@NotNull final BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();

        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)){
            for (ObjectError error : objectErrors){
                errorMsg.append(error.getCode())
                        .append(" - ")
                        .append(error.getDefaultMessage() == null ? error.getCode():error.getDefaultMessage())
                        .append("; ");
            }
        }
        return errorMsg.toString();
    }
}
