package com.example.article_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    // exception handler method
    @ExceptionHandler(value = Exception.class) 
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, Exception ex) {
        log.error("uri : {}", req.getRequestURI());
        log.error("method : {}", req.getMethod());
        log.error("error : {}", ex.getMessage());
        System.out.println("uri : " + req.getRequestURI() + 
                           ", method : " + req.getMethod() + 
                           ", ex : " + ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                    .message(ex.getMessage())
                                    .build();

        return ResponseEntity.ok().body(response);    
    }


    // exception handler method
    @ExceptionHandler(value = ArticleNotFoundException.class) 
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, ArticleNotFoundException ex) {
        log.error("uri : {}", req.getRequestURI());
        log.error("method : {}", req.getMethod());
        log.error("error : {}", ex.getMessage());
        // System.out.println("uri : " + req.getRequestURI() + 
        //                    ", method : " + req.getMethod() + 
        //                    ", ex : " + ex.getMessage());


        ErrorResponse response = ErrorResponse.builder()
                                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))  //400
                                    .message(ex.getMessage())
                                    .build();

        return ResponseEntity.ok().body(response);    
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, MethodArgumentNotValidException ex) {

        log.info("uri : {}, method: {}" + req.getRequestURI(), req.getMethod());

        StringBuilder builder = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            builder.append("[").append(error.getField()).append("] ")
                   .append(error.getDefaultMessage()).
                   append(", Provided value: ")
                   .append(error.getRejectedValue())
                   .append(". ");
        });
        ErrorResponse response = ErrorResponse.builder()
                                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))  //400
                                    .message(builder.toString())
                                    .build();

        return ResponseEntity.ok().body(response);
                          
    }
         
}
