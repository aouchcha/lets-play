package Lets_play.Backend.Configs.ExceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DuplicateKeyExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)   
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> DuplicateKeyExcep(DuplicateKeyException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Already Exist in data base");
        return errors;
    }
}
