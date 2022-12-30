package ru.karod.tsm.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectsResponse {
    private List<SubjectDTO> subjects;

    public SubjectsResponse(List<SubjectDTO> subjects){
        this.subjects = subjects;
    }

}