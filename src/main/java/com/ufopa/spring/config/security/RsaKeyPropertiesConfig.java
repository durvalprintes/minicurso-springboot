package com.ufopa.spring.config.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyPropertiesConfig {

  private RSAPublicKey publicKey;
  private RSAPrivateKey privateKey;

}