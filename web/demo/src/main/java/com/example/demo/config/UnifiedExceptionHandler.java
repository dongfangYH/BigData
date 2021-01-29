package com.example.demo.config;

import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.dto.Response;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-04 15:18
 **/
@Slf4j
@Component
@ControllerAdvice
@ConditionalOnWebApplication
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ErrorResponse handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), e.getMessage());
    }

    /*@ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public ErrorResponse handleServletException(Exception e){
        log.error(e.getMessage(), e);
        return Response.error();
    }*/

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ErrorResponse handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), e.getMessage());
    }

    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ErrorResponse handleServerException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error();
    }
}
