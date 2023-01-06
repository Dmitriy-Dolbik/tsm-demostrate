package ru.karod.tsm.services.email;

import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MapCreator
{
    Map<String, String> createMap(@NotNull final String[] keys, @NotNull final String[] values);
}
