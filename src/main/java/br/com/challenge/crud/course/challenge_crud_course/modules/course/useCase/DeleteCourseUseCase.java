package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class DeleteCourseUseCase {
    
    @Autowired
    CourseRepository  courseRepository;

    @Autowired
    FindByCourseIdUseCase findByCourseIdUseCase;

    public CourseEntity execute(UUID id) {
        var courseToBeDeleted = findByCourseIdUseCase.execute(id);

        courseRepository.delete(courseToBeDeleted);

        return courseToBeDeleted;
    }
}
