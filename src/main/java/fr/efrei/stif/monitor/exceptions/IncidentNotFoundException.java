package fr.efrei.stif.monitor.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IncidentNotFoundException extends RuntimeException {

    public IncidentNotFoundException(String errorMessage, Throwable causeException) {
        super(errorMessage, causeException);
    }

    public IncidentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}