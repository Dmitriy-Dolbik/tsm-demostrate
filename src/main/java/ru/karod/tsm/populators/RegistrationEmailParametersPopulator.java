package ru.karod.tsm.populators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.models.User;
import ru.karod.tsm.util.EmailUtil;

@Component
@RequiredArgsConstructor
public class RegistrationEmailParametersPopulator implements TsmPopulator<User>
{
    private final EmailUtil emailUtil;

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
        String verifyURL = emailUtil.getVerifyURLForUser(source);
        String name = emailUtil.getFullNameForEmailTemplate(source);

        Map<String, String> params = new HashMap<>();
        params.put("${name}", name);
        params.put("${verification_url}", verifyURL);
        return params;
    }
}
