package br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.repository.CourseRepository;

@Service
public class FindCourseUseCase {

    @Autowired
    CourseRepository courseRepository;

    public List<CourseEntity> execute(String name, String category) {
        if (name != null && !name.isBlank() && category != null && !category.isBlank()) {
            return this.courseRepository.findByNameAndCategory(name, category).orElse(Collections.emptyList());
        }

        if (name != null && !name.isBlank()) {
            return this.courseRepository.findByName(name).orElse(Collections.emptyList());
        }

        if (category != null && !category.isBlank()) {
            return this.courseRepository.findByCategory(category).orElse(Collections.emptyList());
        }

        return this.courseRepository.findAll();
    }
}
