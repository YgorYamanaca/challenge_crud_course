package br.com.challenge.crud.course.challenge_crud_course.modules.course.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.challenge.crud.course.challenge_crud_course.modules.course.dto.CourseErrorMessageDTO;
import br.com.challenge.crud.course.challenge_crud_course.modules.course.model.Status;

@ControllerAdvice
public class CourseExceptionHandler {

    private MessageSource messageSource;

    public CourseExceptionHandler(MessageSource message) {
        this.messageSource = message;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CourseErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        List<CourseErrorMessageDTO> errorMessageDTO = new ArrayList<>();
        error.getBindingResult().getFieldErrors().forEach(err -> {
            String errorMessage = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            CourseErrorMessageDTO formatedErrorMessage = new CourseErrorMessageDTO(errorMessage, err.getField());
            errorMessageDTO.add(formatedErrorMessage);
        });

        return ResponseEntity.badRequest().body(errorMessageDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String enumValues = Arrays.toString(Status.values());
        String message = "Status needs to be " + enumValues;
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String enumValues = Arrays.toString(Status.values());
        String message = "Status needs to be " + enumValues;
        return ResponseEntity.badRequest().body(message);
    }
}
