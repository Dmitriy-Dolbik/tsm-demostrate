package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karod.tsm.models.Subject;
import ru.karod.tsm.services.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @GetMapping()
    public ResponseEntity<List<Subject>> getSubjects() {
        List<Subject> subjects = subjectService.findAll().stream().toList();
        return ResponseEntity.ok(subjects);
    }
}