package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseNameAndCategoryDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseMissingInfoException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.exception.CourseNotFoundException;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class UpdateCourseUseCase {
    
    @Autowired
    CourseRepository courseRepository;

    public CourseEntity execute(UUID courseId, CourseNameAndCategoryDTO courseNameAndCategoryDTO) {
        var courseName = courseNameAndCategoryDTO.getName();
        var courseCategory = courseNameAndCategoryDTO.getCategory();
        if (courseId == null)
            throw new CourseMissingInfoException("Course ID");
        if (courseName == null || courseName.isBlank() && courseCategory == null || courseCategory.isBlank())
            throw new CourseMissingInfoException("Course Name or Course Category");

        if(courseRepository.findById(courseId).isEmpty()) {
            throw new CourseNotFoundException();
        }

        CourseEntity courseToBeUpdated = courseRepository.findById(courseId).orElseThrow(() -> {
            throw new CourseNotFoundException();
        });

        if(!courseName.isBlank()) {
            courseToBeUpdated.setName(courseName);
        }

        if(!courseCategory.isBlank()) {
            courseToBeUpdated.setCategory(courseCategory);
        }

        return courseRepository.save(courseToBeUpdated);
    }
}
