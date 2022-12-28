package ru.karod.tsm.util;

import javax.persistence.AttributeConverter;
import java.util.UUID;

@javax.persistence.Converter(autoApply = true)
public class PostgresUuidConverter implements AttributeConverter<UUID, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }

}
