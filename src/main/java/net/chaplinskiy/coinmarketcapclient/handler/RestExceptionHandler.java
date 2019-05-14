package net.chaplinskiy.coinmarketcapclient.handler;

import net.chaplinskiy.coinmarketcapclient.error.ErrorDetail;
import net.chaplinskiy.coinmarketcapclient.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleRsourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(LocalDateTime.now());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<Object>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setTimeStamp(LocalDateTime.now());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

}
