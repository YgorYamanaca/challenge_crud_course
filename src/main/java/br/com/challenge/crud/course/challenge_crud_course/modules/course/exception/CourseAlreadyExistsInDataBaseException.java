package br.com.challenge.crud.course.challenge_crud_course.modules.course.exception;

public class CourseAlreadyExistsInDataBaseException extends RuntimeException {
    public CourseAlreadyExistsInDataBaseException() {
        super("Course with this name and this category already exists.");
    }
}
