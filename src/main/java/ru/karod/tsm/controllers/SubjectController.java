package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karod.tsm.dto.SubjectDTO;
import ru.karod.tsm.dto.SubjectsResponse;
import ru.karod.tsm.services.SubjectService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class SubjectController {
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public SubjectsResponse getSubjects() {
        // оборачиваем список в объект для пересылки
        return new SubjectsResponse(subjectService.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList()));
    }
}
