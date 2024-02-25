package guru.springframework.spring6webmvc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason="The value has not been found.")
public class NotfoundException extends RuntimeException{
    public NotfoundException() {
    }

    public NotfoundException(String message) {
        super(message);
    }

    public NotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotfoundException(Throwable cause) {
        super(cause);
    }

    public NotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
