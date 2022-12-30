package ru.karod.tsm.models;

import lombok.Data;
import ru.karod.tsm.models.enums.Language;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "id",columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "title")
    @Enumerated (EnumType.STRING)
    private Language language;
}
