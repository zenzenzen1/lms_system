package com.example.identity_service.exception;

import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.identity_service.dto.response.ApiResponse;

// @ControllerAdvice
@RestControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();

        ErrorCode errorCode;
        try {

            errorCode = ErrorCode.valueOf(enumKey);

            // get properties from annotation
            var constraintViolation = e.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            var attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());

        } catch (IllegalArgumentException illegalArgumentException) {
            errorCode = ErrorCode.INVALID_KEY;
        }

        ApiResponse<String> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    ResponseEntity<String> handleArithmeticException(ArithmeticException e) {
        System.out.println("lỗi tính toán rùi");
        return ResponseEntity.ok().body("ArithmeticException: " + e.getMessage());
    }
    
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<String>> handleJwtException(JwtException ex) {
        log.error("JWT Exception: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(1001)
                .message("Invalid JWT token: " + ex.getMessage())
                .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad Credentials: {}", ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(1002)
                .message("Invalid credentials")
                .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
    //     log.error("Unexpected error: {}", ex.getMessage(), ex);
        
    //     ApiResponse<String> response = ApiResponse.<String>builder()
    //             .code(9999)
    //             .message("An unexpected error occurred")
    //             .build();
        
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    // }
}
