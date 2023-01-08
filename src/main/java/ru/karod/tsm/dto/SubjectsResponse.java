package ru.karod.tsm.dto;

import lombok.Data;
import ru.karod.tsm.models.Subject;

import java.util.List;

@Data
public class SubjectsResponse {
    private List<SubjectDTO> subjectList;

    public SubjectsResponse(List<SubjectDTO> subjectList){
        this.subjectList = subjectList;
    }
}
