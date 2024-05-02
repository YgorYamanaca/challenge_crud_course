package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseMissingInfoException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseNotFoundException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class DeleteCourseUseCase {
    
    @Autowired
    CourseRepository  courseRepository;

    public CourseEntity execute(UUID id) {
        if(id == null) throw new CourseMissingInfoException("Course Id");

        var courseToBeDeleted = courseRepository.findById(id).orElseThrow(() -> {
            throw new CourseNotFoundException();
        });

        courseRepository.delete(courseToBeDeleted);

        return courseToBeDeleted;
    }
}
