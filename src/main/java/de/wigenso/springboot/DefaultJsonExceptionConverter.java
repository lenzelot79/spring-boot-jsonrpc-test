package de.wigenso.springboot;

import com.fasterxml.jackson.databind.JsonNode;
import de.wigenso.springboot.jsonrpc.JsonExceptionConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultJsonExceptionConverter implements JsonExceptionConverter {

    @ExceptionHandler(RuntimeException.class)
    public JsonNode convertRuntimeException(RuntimeException e) {
        return messageToJsonNode(e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public JsonNode convertAccessDeniedException(AccessDeniedException e) {
        return messageToJsonNode(e);
    }

}
