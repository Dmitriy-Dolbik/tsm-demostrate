package ru.karod.tsm.dto;

import lombok.Data;
import ru.karod.tsm.models.enums.Language;

import javax.validation.constraints.NotNull;

@Data
public class SubjectDTO {

    @NotNull
    private Language language;
}
