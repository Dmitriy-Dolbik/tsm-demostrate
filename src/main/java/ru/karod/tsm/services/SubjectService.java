package ru.karod.tsm.services;

import java.util.List;

import javax.validation.constraints.NotNull;

import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.Subject;

public interface SubjectService
{
    List<Subject> findAll();
    Subject findSubjectById(@NotNull final String subjectId);
}
