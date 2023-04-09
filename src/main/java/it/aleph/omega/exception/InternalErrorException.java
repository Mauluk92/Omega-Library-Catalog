package it.aleph.omega.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
@Data
public class InternalErrorException extends RuntimeException{
}
