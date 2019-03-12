package com.micro.response.exception;


import com.micro.response.exception.global.ExceptionInterface;
import com.micro.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = GlobalCustomException.class)
    public ExceptionResponse errorHandlerOverJson(HttpServletRequest request,
                                                  GlobalCustomException exception) {
        ExceptionInterface exceptionInterface=exception.getExceptionInterface();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), exceptionInterface.getStatus(), null, exceptionInterface.getMessage(), request.getRequestURI());
    }

    /**
     * Handle sql exception exception response.
     *
     * @param request      the request
     * @param sqlException the sql exception
     * @return the exception response
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse handleSQLException(HttpServletRequest request, SQLException sqlException) {
        log.error("Occurred sql exception,Returning HTTP 500 Internal Server Error", sqlException);
        String message = sqlException.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQLException occurred", message, request.getRequestURI());
    }

    /**
     * Handle io exception.
     *
     * @param ioException the io exception
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ExceptionResponse handleIOException(HttpServletRequest request, IOException ioException) {
        log.error("Occurred io exception,Returning HTTP 404 Not Found", ioException);
        String message = ioException.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), message, request.getRequestURI());
    }

    /**
     * handler illegal argument exception
     *
     * @param request                  the request
     * @param illegalArgumentException the illegal argument exception
     * @return the exception response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException illegalArgumentException) {
        log.error("Occurred illegalArgumentException, Returning HTTP 400 Bad Request", illegalArgumentException);
        String message = illegalArgumentException.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException constraintViolationException) {
        log.error("Occurred ConstraintViolationException, Returning HTTP 400 Bad Request", constraintViolationException);
        String message = constraintViolationException.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI());
    }

    /**
     * handle method argument type mismatch Exception
     *
     * @param request   the request
     * @param exception the method argument type mismatch Exception
     * @return the exception response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse handleMethodArgumentTypeMismatchException(HttpServletRequest request, Exception exception) {
        log.error("Occurred MethodArgumentTypeMismatchException,Returning HTTP 500 Internal Server Error", exception);
        String message = exception.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, request.getRequestURI());
    }
    /**
     * 状态非法错误
     *
     * @param request   the request
     * @param exception the IllegalStateException exception
     * @return the exception response
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ExceptionResponse handleOtherException(HttpServletRequest request, IllegalStateException exception) {
        log.error("Returning HTTP 403 forbidden Error", exception);
        String message = exception.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message, request.getRequestURI());
    }

    /**
     * 其它未知错误类型的处理
     *
     * @param request   the request
     * @param exception the unknown exception
     * @return the exception response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse handleOtherException(HttpServletRequest request, Exception exception) {
        log.error("Returning HTTP 500 Internal Server Error", exception);
        String message = exception.getMessage();
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        log.error("Occurred MethodArgumentNotValidException, Returning HTTP 500 internal server error!", exception);
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errorMap = new HashMap<String, String>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errorMap.put(field, defaultMessage);
        }
        return ExceptionResponse.create(DateUtils.formatDate(new Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Parameter valid Invalid!", errorMap, request.getRequestURI());
    }

}