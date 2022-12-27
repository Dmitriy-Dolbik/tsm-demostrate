package ru.karod.tsm.util;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorUtil {
    public static String createErrorMessageToClient(BindingResult bindingResult){
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
