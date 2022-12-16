package com.ufopa.spring.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getRequestURI().contains("/home") || request.getRequestURI().contains("/login");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String autorizacao = request.getHeader("Authorization");
    if (StringUtils.hasText(autorizacao) && autorizacao.startsWith("Bearer ")) {
      String username = null;
      try {
        username = this.getUsernameFromToken(autorizacao.substring(7));
      } catch (Exception e) {
        throw new AuthenticationCredentialsNotFoundException("Token invalido");
      }
      if (StringUtils.hasText(username)) {
        UserDetails usuario = userInMemory.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usuarioAutenticado = new UsernamePasswordAuthenticationToken(
            usuario, null, usuario.getAuthorities());
        usuarioAutenticado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        log.debug("Usuario autenticado: {}", SecurityContextHolder.getContext().getAuthentication().getName());
      }
    } else {
      throw new AuthenticationCredentialsNotFoundException("Token nao encontrado");
    }
    filterChain.doFilter(request, response);
  }

  private String getUsernameFromToken(String token) {
    return decoder.decode(token).getSubject();
  }

}