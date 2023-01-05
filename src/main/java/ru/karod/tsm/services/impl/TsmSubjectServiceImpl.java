package ru.karod.tsm.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.Subject;
import ru.karod.tsm.models.enums.Language;
import ru.karod.tsm.repositories.SubjectRepository;
import ru.karod.tsm.services.SubjectService;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class TsmSubjectServiceImpl implements SubjectService
{
    private final SubjectRepository subjectRepository;

    //@PostConstruct // - включаем аннотацию, чтобы метод выполнился
    // один раз после запуска приложения, создав все subjects из Enum
    public void createAllSubjects()
    {
        for (Language language : Language.values())
        {
            Subject subject = new Subject();
            subject.setId(UUID.randomUUID().toString());
            subject.setLanguage(language);
            subjectRepository.save(subject);
        }
    }

    @Override
    public List<Subject> findAll()
    {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findSubjectById(@NotNull final String subjectId)
    {
        return subjectRepository.findById(subjectId).orElseThrow(() ->
                new NotFoundException("Subject with id: " + subjectId + " cannot be found"));
    }
}
