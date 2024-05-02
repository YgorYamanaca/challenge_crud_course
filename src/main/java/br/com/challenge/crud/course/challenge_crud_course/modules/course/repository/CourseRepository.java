package br.com.challenge.crud.course.challenge_crud_course.modules.course.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<List<CourseEntity>> findByName(String name);
    Optional<List<CourseEntity>> findByCategory(String category);
    Optional<List<CourseEntity>> findByNameAndCategory(String name, String category);
    @NonNull Optional<CourseEntity> findById(@NonNull UUID id);
}
