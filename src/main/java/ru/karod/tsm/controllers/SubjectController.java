package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karod.tsm.dto.SubjectDTO;
import ru.karod.tsm.dto.SubjectsResponse;
import ru.karod.tsm.services.impl.TsmSubjectServiceImpl;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final TsmSubjectServiceImpl tsmSubjectServiceImpl;
    private final ModelMapper modelMapper;
    @GetMapping()
    public ResponseEntity<SubjectsResponse> getSubjects() {
        return ResponseEntity.ok(new SubjectsResponse(tsmSubjectServiceImpl.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .toList()));
    }
}