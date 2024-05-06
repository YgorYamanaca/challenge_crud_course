package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseAlreadyInThisStatusException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.model.Status;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class ActiveCourseUseCase {
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    FindByCourseIdUseCase findByCourseIdUseCase;

    public CourseEntity execute(UUID id, Status status) {
        var foundCourse = this.findByCourseIdUseCase.execute(id);

        if(status == Status.ACTIVE) {
            if(foundCourse.getStatus() == Status.DESACTIVE) {
                foundCourse.setStatus(Status.ACTIVE);
            } else {
                throw new CourseAlreadyInThisStatusException(Status.ACTIVE);
            }
        } else {
            if(foundCourse.getStatus() == Status.ACTIVE) {
                foundCourse.setStatus(Status.DESACTIVE);
            } else {
                throw new CourseAlreadyInThisStatusException(Status.DESACTIVE);
            }
        }

        return this.courseRepository.save(foundCourse);
    }
}
