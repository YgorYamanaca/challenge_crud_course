package br.com.challenge.crud.course.challenge_crud_course.modules.course.exception;

public class CourseMissingInfoException extends RuntimeException {
    public CourseMissingInfoException(String fieldName) {
        super("The field(s) is missing " + fieldName);
    }
}
