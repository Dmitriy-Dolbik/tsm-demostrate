package ru.karod.tsm.util.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.services.email.EmailSender;
import ru.karod.tsm.util.ErrorUtil;

/**
 * @inheritance {@link ErrorUtil}
 */
@Component
public class TsmErrorUtilImpl implements ErrorUtil
{
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
