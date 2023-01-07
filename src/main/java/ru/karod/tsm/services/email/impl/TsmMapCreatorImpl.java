package ru.karod.tsm.services.email.impl;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ru.karod.tsm.services.email.MapCreator;

@Component
public class TsmMapCreatorImpl implements MapCreator
{

    @Override
    public Map<String,String> createMap(final @NotNull String[] keys, final @NotNull String[] values)
    {
        if (keys == null || values == null || keys.length != values.length) {
            throw new IllegalArgumentException("Arrays must not be null and must have the same number of elements.");
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
