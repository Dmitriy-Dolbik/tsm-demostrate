package ru.karod.tsm.populators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ru.karod.tsm.models.User;
import ru.karod.tsm.util.EmailUtil;

import static ru.karod.tsm.util.EmailUtil.getFullNameForEmailTemplate;
import static ru.karod.tsm.util.EmailUtil.getVerifyURLForUser;

@Component
public class RegistrationEmailParametersPopulator implements TsmPopulator<User>
{
    private EmailUtil emailUtil;

    /**
     * Имя
     * Фамилия
     * Линка верификации
     *
     * @param source
     * @return
     */
    @Override
    public Map<String, String> populate(final User source)
    {
        String verifyURL = getVerifyURLForUser(source);
        String name = getFullNameForEmailTemplate(source);

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("verificationURL", verifyURL);
        return params;
    }
}
