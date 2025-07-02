package com.example.schedule_service.exception;

import java.util.ConcurrentModificationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.schedule_service.entity.dto.response.ApiResponse;


// @ControllerAdvice
@RestControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception exception) {
        log.error("Exception: ", exception.getLocalizedMessage());
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(value = ConcurrentModificationException.class)
    ResponseEntity<ApiResponse<Void>> handlingConcurrentModificationException(ConcurrentModificationException exception) {
        log.error("ConcurrentModificationException: ", exception.getLocalizedMessage());
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage("ConcurrentModificationException. Please try again.");

        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(value = IdNotFoundException.class)
    ResponseEntity<ApiResponse<Void>> handlingIdNotFoundException(IdNotFoundException exception) {
        log.error("IdNotFoundException: ", exception.getLocalizedMessage());
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

    @ExceptionHandler(value = ArithmeticException.class)
    ResponseEntity<String> handleArithmeticException(ArithmeticException e) {
        System.out.println("lỗi tính toán rùi");
        return ResponseEntity.ok().body("ArithmeticException: " + e.getMessage());
    }
}
