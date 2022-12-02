package com.ufopa.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthRequestFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userInMemory;

  @Autowired
  private JwtDecoder decoder;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String autorizacao = request.getHeader("Authorization");
    String subjectToken = null;
    String token = null;
    if (!request.getRequestURI().endsWith("/token") &&
        autorizacao != null && autorizacao.startsWith("Bearer ")) {
      token = autorizacao.substring(7);
      try {
        subjectToken = this.getSubjectFromToken(token);
      } catch (Exception e) {
        log.error("Token invalido");
      }

      if (subjectToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails usuario = userInMemory.loadUserByUsername(subjectToken);
        UsernamePasswordAuthenticationToken usuarioAutenticado = new UsernamePasswordAuthenticationToken(
            usuario, null, usuario.getAuthorities());
        usuarioAutenticado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        log.debug("Usuario autenticado: {}", SecurityContextHolder.getContext().getAuthentication().getName());
      }

    } else {
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }

  private String getSubjectFromToken(String token) {
    return decoder.decode(token).getSubject();
  }

}