package guru.springframework.spring6webmvc.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException e){
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        if(e.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException) e.getCause().getCause();
            List errors = cve.getConstraintViolations()
                    .stream().map(violation->{
                        Map<String,String> errMap = new HashMap<>();
                        errMap.put(violation.getPropertyPath().toString(),
                                violation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());
            return bodyBuilder.body(errors);
        }
        return bodyBuilder.build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindingErrors(MethodArgumentNotValidException e){
        List list = e.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> map = new HashMap<>();
                    map.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return map;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(list);
    }
}
