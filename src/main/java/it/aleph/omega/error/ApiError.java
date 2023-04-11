package it.aleph.omega.error;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ApiError {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
    private List<ErrorMessage> errors;
}
