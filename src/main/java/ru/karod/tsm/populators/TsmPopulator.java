package ru.karod.tsm.populators;

import java.util.Map;

/**
 * Популирует значения из source и отдает мапу
 * @param <T>
 */
public interface TsmPopulator<T>
{
    Map<String, String> populate(T source);
}
