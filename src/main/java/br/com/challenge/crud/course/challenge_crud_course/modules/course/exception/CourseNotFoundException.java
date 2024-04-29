package br.com.challenge.crud.course.challenge_crud_course.modules.course.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() {
        super("Course not found");
    }
}
