package br.com.challenge.crud.course.challenge_crud_course.modules.course.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseNameAndCategoryDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.model.Status;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.ActiveCourseUseCase;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.CreateCourseUseCase;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.DeleteCourseUseCase;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.FindCourseUseCase;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.useCase.UpdateCourseUseCase;
import jakarta.validation.Valid;


@RestController
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;
    @Autowired
    private FindCourseUseCase findCourseUseCase;
    @Autowired
    private UpdateCourseUseCase updateCourseUseCase;
    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;
    @Autowired
    private ActiveCourseUseCase activeCourseUseCase;

    @PostMapping(value = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNewCourse(@Valid @RequestBody CourseNameAndCategoryDTO courseNameAndCategoryDTO) {
        try {
            UriComponents newCourseURI = UriComponentsBuilder.fromUriString("/courses").build();
            return ResponseEntity.created(newCourseURI.toUri()).body(this.createCourseUseCase.execute(courseNameAndCategoryDTO));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.FOUND).body(error.getMessage());
        }
    }

    @GetMapping("/course")
    public ResponseEntity<Object> getCourses(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category) {
        var courseList = this.findCourseUseCase.execute(name, category);

        if (courseList.size() >= 1) {
            return ResponseEntity.ok().body(courseList);
        }

        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/course/{id}")
    public ResponseEntity<Object> updateCourse(
        @PathVariable UUID id,
        @RequestBody CourseNameAndCategoryDTO courseNameAndCategoryDTO
    ) {
        if (id == null)
            return ResponseEntity.badRequest().body("Please enter the course ID for update");
        if (courseNameAndCategoryDTO.getName() == null && courseNameAndCategoryDTO.getCategory() == null)
            return ResponseEntity.badRequest().body("Name or category is missing to change course with id " + id);

        try {
            return ResponseEntity.ok().body(this.updateCourseUseCase.execute(id, courseNameAndCategoryDTO));
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());   
        }
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID id) {
        if (id == null)
            return ResponseEntity.badRequest().body("Please enter the course ID for delete");
        
            try {
                return ResponseEntity.ok().body(this.deleteCourseUseCase.execute(id));
            } catch (Exception error) {
                return ResponseEntity.internalServerError().body(error.getMessage()); 
            }
    }

    @PatchMapping("/course/{id}/{status}")
    public ResponseEntity<Object> toggleCourseStatus(@PathVariable UUID id, @PathVariable Status status) {
        if(id == null) return ResponseEntity.badRequest().body("Please enter the course ID for update");
        if (status == null)
            return ResponseEntity.badRequest().body("Please enter ACTIVE or DESACTIVE to change course status");

        try {
            return ResponseEntity.ok().body(this.activeCourseUseCase.execute(id, status));
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }

    }
}
