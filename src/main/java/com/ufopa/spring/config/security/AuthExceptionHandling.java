package com.ufopa.spring.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthExceptionHandling implements AuthenticationEntryPoint, AccessDeniedHandler {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    log.error("Access Unauthorized");
    response.addHeader("Not-Authorized-Reason", "bad-credentials");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.error("Access Denied");
    response.addHeader("Access-Denied-Reason", "no-permission");
    response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());

  }

}
