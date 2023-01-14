package ru.karod.tsm.populators;

import java.util.Map;

import ru.karod.tsm.models.enums.EmailType;

/**
 * The class for collecting parameters and returning a Map
 * @param <T>
 */
public interface TsmPopulator<T>
{
    /**
     * @param source any type
     * @return map with parameters where the keys are names of placeholders of html templates, and
     * the values are the significances for replacing placeholders.
     */
    Map<String, String> populate(T source);
}
