package com.ufopa.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ufopa.spring"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Minicurso de Spring Boot")
        .description("Programação Projetos e Carreiras TI - UFOPA - Campus Oriximiná")
        .contact(new Contact("Durval Printes", "https://www.linkedin.com/in/durval-printes-51880359/",
            "durvalprintes@gmail.com"))
        .version("1.0").build();
  }

}