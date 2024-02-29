package com.johanrincon.ms.users.controllers;

import com.johanrincon.ms.users.exceptions.CustomException;
import com.johanrincon.ms.users.utils.HeadersUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.johanrincon.ms.users.dtos.MessageDTO;

import java.io.IOException;

import static com.johanrincon.ms.users.enums.InternalCodes.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionHandlerController {

    static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Value("500")
    private String statusMessageNok;

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    ResponseEntity<MessageDTO> customException(final CustomException ex) {
        return new ResponseEntity<>(
                new MessageDTO(String.valueOf(ex.getInternalCodes().getHttpStatusCode()),ex.getMessage()),BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<MessageDTO> handleException(final Exception ex) {
        logger.info("handleException - init");
        logger.error("Se ha capturado un error", ex);
        MessageDTO response = handleResponseMessage(ex);
        logger.info("handleException - end");
        return new ResponseEntity<>(response, HeadersUtils.getGenericHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method should handle every kind of exception and map it to an HTTP response
     * @param ex {@link Exception} An exception to be handled
     * @return {@link MessageDTO} A response message mapped from the handled exception
     */
    private MessageDTO handleResponseMessage(final Exception ex) {
        if(ex instanceof IOException) { 
            return new MessageDTO(statusMessageNok, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        else if(ex instanceof InterruptedException) { 
            return new MessageDTO(statusMessageNok, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        else if(ex instanceof NullPointerException) { 
            return new MessageDTO(statusMessageNok, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        else if(ex instanceof RuntimeException) { 
            return new MessageDTO(statusMessageNok, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        else { 
            return new MessageDTO(statusMessageNok, INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

}