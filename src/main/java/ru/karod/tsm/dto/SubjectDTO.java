package ru.karod.tsm.dto;

import lombok.Data;
import ru.karod.tsm.models.enums.Language;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
public class SubjectDTO {
    private String id;

    private Language language;
}
