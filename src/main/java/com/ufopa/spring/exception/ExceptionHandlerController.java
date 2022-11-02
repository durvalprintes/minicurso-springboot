package com.ufopa.spring.exception;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDto handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
      WebRequest request) {
    return ErrorDto
        .builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao("Entrada de dados inválida")
        .listaErros(
            exception.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErrorCampoDto(e.getField(), e.getDefaultMessage())).collect(Collectors.toList()))
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ErrorDto handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
      HttpServletRequest request) {
    return ErrorDto
        .builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao("Parâmetro incorreto: " + exception.getValue())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ErrorDto handlerHttpMessageNotReadableException(HttpMessageNotReadableException exception,
      HttpServletRequest request) {
    return ErrorDto
        .builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao("JSON não encontrado ou mal formatado.")
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(SearchException.class)
  public ErrorDto handlerSearchException(SearchException exception, HttpServletRequest request) {
    return ErrorDto
        .builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .descricao(exception.getMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ErrorDto handlerResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
    return ErrorDto
        .builder()
        .status(HttpStatus.NOT_FOUND.value())
        .descricao(Optional.ofNullable(exception.getMessage()).orElse("Cliente não encontrado"))
        .build();
  }

}
