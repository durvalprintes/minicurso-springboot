package com.ufopa.spring.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandleController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
    return ErrorDto
        .builder()
        .data(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao("Entrada de dados inválida")
        .listaErros(
            exception.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErrorCampoDto(e.getField(), e.getDefaultMessage())).collect(Collectors.toList()))
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DateTimeParseException.class)
  public ErrorDto handleHDateTimeParseException(DateTimeParseException exception, HttpServletRequest request) {
    return ErrorDto
        .builder()
        .data(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao("Data inválida - " + exception.getMessage())
        .build();
  }

}
