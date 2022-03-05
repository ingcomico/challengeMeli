package co.com.meli.challenger.handler;

import co.com.meli.challenger.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        ErrorInfo errorInfo = new ErrorInfo("Error", request.getRequestURI(), System.currentTimeMillis());
        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errors = "";
        for (FieldError fieldError : fieldErrors) {
            errors = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            errorInfo.getListMessage().add(errors);
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorInfo> methodCustomException(HttpServletRequest request, CustomException e) {
        e.printStackTrace();
        ErrorInfo errorInfo = new ErrorInfo("Error", request.getRequestURI(), System.currentTimeMillis());
        errorInfo.getListMessage().add(e.getMessage());
        HttpStatus httpStatus = e.getStatusError();
        return new ResponseEntity<>(errorInfo, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> methodException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        ErrorInfo errorInfo = new ErrorInfo("Error", request.getRequestURI(), System.currentTimeMillis());
        errorInfo.getListMessage().add(e.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
