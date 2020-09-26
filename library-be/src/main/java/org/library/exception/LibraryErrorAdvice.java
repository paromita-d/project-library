package org.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class LibraryErrorAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleRunTimeException(RuntimeException e) {
        return generateError(e);
    }

    @ExceptionHandler({LibraryException.class})
    public ResponseEntity<Map<String, String>> handleLibraryException(LibraryException e) {
        return generateError(e);
    }

    private ResponseEntity<Map<String, String>> generateError(Exception e) {
        log.error("Exception: ", e);
        Map<String, String> errorMap = new LinkedHashMap<>();
        errorMap.put("errorBody", e.getMessage());
        errorMap.put("httpStatus", INTERNAL_SERVER_ERROR.getReasonPhrase());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMap);
    }
}
