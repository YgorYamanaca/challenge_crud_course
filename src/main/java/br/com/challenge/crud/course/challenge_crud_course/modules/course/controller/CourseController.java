package br.com.challenge.crud.course.challenge_crud_course.modules.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseNameAndCategoryDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.CreateCourseUseCase;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.FindCourseUseCase;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;
    @Autowired
    private FindCourseUseCase findCourseUseCase;
    
    @PostMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNewCourse(@Valid @RequestBody CourseNameAndCategoryDTO courseNameAndCategoryDTO) {
        try {
            UriComponents newCourseURI = UriComponentsBuilder.fromUriString("/courses").build();
            return ResponseEntity.created(newCourseURI.toUri()).body(this.createCourseUseCase.execute(courseNameAndCategoryDTO));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.FOUND).body(error.getMessage());
        }
    }

    @GetMapping("/courses")
    public ResponseEntity<Object> getMethodName(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category) {
        var courseList = this.findCourseUseCase.execute(name, category);

        if (courseList.size() > 1) {
            return ResponseEntity.ok().body(courseList);
        }

        return ResponseEntity.notFound().build();
    }
    
}
