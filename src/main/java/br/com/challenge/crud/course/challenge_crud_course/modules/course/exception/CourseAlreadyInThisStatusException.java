package br.com.challenge.crud.course.challenge_crud_course.modules.course.exception;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.model.Status;

public class CourseAlreadyInThisStatusException extends RuntimeException {
    public CourseAlreadyInThisStatusException(Status status) {
        super("This course already " + status);
    }
}
