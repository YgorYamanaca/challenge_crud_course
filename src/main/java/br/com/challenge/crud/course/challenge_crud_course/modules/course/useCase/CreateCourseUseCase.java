package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseNameAndCategoryDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseAlreadyExistsInDataBaseException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(CourseNameAndCategoryDTO courseNameAndCategoryDTO) {
        this.courseRepository.findByNameAndCategory(courseNameAndCategoryDTO.getName(), courseNameAndCategoryDTO.getCategory())
            .ifPresent((course) -> {
                if(course.size() > 1) {
                    throw new CourseAlreadyExistsInDataBaseException();
                }
            });
        CourseEntity newCourse = CourseEntity.builder()
                .name(courseNameAndCategoryDTO.getName())
                .category(courseNameAndCategoryDTO.getCategory())
                .build();
        return this.courseRepository.save(newCourse);
    }
}
