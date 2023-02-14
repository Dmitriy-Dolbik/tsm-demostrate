package ru.karod.tsm.populators.impl;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.populators.TsmPopulator;
import ru.karod.tsm.util.UserUtil;
import ru.karod.tsm.util.impl.TsmUserUtilImpl;

/**
 * The class for collecting parameters which will be used to replace placeholders of the html template for
 * email with type {@link EmailType #REGISTRATION}
 */
@Component
@RequiredArgsConstructor
public class RegistrationEmailParametersPopulator implements TsmPopulator<User>
{
    private final UserUtil tsmUserUtilImpl;

    /**
     * @param source user
     * @return map with parameters for email with type {@link EmailType#REGISTRATION},
     * where keys contain verification_url and full name of user.
     * If user's first and last name are null then the full name is replaced with "customer"
     */
    @Override
    public Map<String, String> populate(@NotNull final User source)
    {
        String verifyURL = tsmUserUtilImpl.getVerifyURLForUser(source);
        String userFullName = tsmUserUtilImpl.getUserFullNameForEmailTemplate(source);

        Map<String, String> params = new HashMap<>();
        params.put("${fullName}", userFullName);
        params.put("${verification_url}", verifyURL);

        return params;
    }
}
