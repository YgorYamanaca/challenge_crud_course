package br.com.challenge.crud.course.challenge_crud_course.modules.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CourseNameAndCategoryDTO {
    @NotBlank(message = "Name not be null or empty.")
    private String name;
    @NotBlank(message = "Category not be null or empty.")
    private String category;
}
