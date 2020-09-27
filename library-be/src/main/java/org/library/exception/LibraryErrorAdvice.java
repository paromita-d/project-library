package org.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.library.controller.dto.StatusDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class LibraryErrorAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<StatusDTO> handleRunTimeException(RuntimeException e) {
        return generateError(e);
    }

    @ExceptionHandler({LibraryException.class})
    public ResponseEntity<StatusDTO> handleLibraryException(LibraryException e) {
        return generateError(e);
    }

    private ResponseEntity<StatusDTO> generateError(Exception e) {
        log.error("Exception: ", e);

        StatusDTO statusDTO = StatusDTO.builder().
                status(INTERNAL_SERVER_ERROR.getReasonPhrase()).
                message(e.getMessage()).
                build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(statusDTO);
    }
}
