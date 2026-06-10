package ra.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.demo.exception.BusinessException;
import ra.demo.exception.DuplicateResourceException;
import ra.demo.exception.ResourceNotFoundException;
import ra.demo.model.dto.response.ApiDataResponse;

import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class APIControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new TreeMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Tham số không hợp lệ",
                null,
                errors,
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiDataResponse<String>> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi xử lý nghiệp vụ",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiDataResponse<String>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi trùng lặp dữ liệu",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiDataResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi không tìm thấy dữ liệu",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiDataResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi không đúng dữ liệu",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiDataResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi sai tham số truyền tới",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }
}
