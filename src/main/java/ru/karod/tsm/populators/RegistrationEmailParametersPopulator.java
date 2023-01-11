package ru.karod.tsm.populators;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.models.User;
import ru.karod.tsm.models.enums.EmailType;
import ru.karod.tsm.util.UserUtil;

@Component
@RequiredArgsConstructor
public class RegistrationEmailParametersPopulator implements TsmPopulator<User>
{
    private final UserUtil userUtil;

    /**
     * @param source user
     * @return map with parameters for email with type {@link EmailType#REGISTRATION},
     * where keys contain verification_url and full name of user.
     * If user's first and last name are null then the full name is replaced with "customer"
     */
    @Override
    public Map<String, String> populate(@NotNull final User source)
    {
        String verifyURL = userUtil.getVerifyURLForUser(source);
        String fullName = userUtil.getFullNameForEmailTemplate(source);

        Map<String, String> params = new HashMap<>();
        params.put("${fullName}", fullName);
        params.put("${verification_url}", verifyURL);
        return params;
    }
}
