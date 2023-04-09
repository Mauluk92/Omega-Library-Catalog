package it.aleph.omega.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class NotFoundException extends RuntimeException{
    private List<Long> idListNotFound;
    private String message;
}
