package br.com.challenge.crud.course.challenge_crud_course.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseErrorMessageDTO {
    
    private String message;
    private String field;
}
