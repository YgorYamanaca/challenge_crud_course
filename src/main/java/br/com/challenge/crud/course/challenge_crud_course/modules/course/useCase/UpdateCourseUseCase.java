package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseNameAndCategoryDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseAlreadyExistsInDataBaseException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseMissingInfoException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseNotFoundException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class UpdateCourseUseCase {
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    FindCourseUseCase findCourseUseCase;

    public CourseEntity execute(UUID courseId, CourseNameAndCategoryDTO courseNameAndCategoryDTO) {
        var courseName = courseNameAndCategoryDTO.getName();
        var courseCategory = courseNameAndCategoryDTO.getCategory();
        if (courseId == null)
            throw new CourseMissingInfoException("Course ID");
        if (courseName == null && courseCategory == null)
            throw new CourseMissingInfoException("Course Name or Course Category");
        CourseEntity foundCourse = courseRepository.findById(courseId).orElseThrow(() -> {
            throw new CourseNotFoundException();
        });

        if(foundCourse.getName().equals(courseName) && foundCourse.getCategory().equals(courseCategory)) {
            throw new CourseAlreadyExistsInDataBaseException();
        }

        if(courseName != null && !courseName.isBlank()) {
            foundCourse.setName(courseName);
        }

        if(courseCategory != null && !courseCategory.isBlank()) {
            foundCourse.setCategory(courseCategory);
        }

        if(findCourseUseCase.execute(foundCourse.getName(), foundCourse.getCategory()).size() >= 1) {
            throw new CourseAlreadyExistsInDataBaseException();
        }

        return courseRepository.save(foundCourse);
    }
}
