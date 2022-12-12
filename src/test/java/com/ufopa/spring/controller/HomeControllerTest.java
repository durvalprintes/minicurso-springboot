package com.ufopa.spring.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ufopa.spring.config.security.SecurityCoreConfig;

@ActiveProfiles("test")
@WebMvcTest(controllers = HomeController.class)
@Import(value = SecurityCoreConfig.class)
public class HomeControllerTest {

  private static final String PATH_HELLO_ENDPOINT = "/home/hello";

  @Autowired
  private MockMvc mockRequest;

  @Test
  void deveriaRetornarConteudoDaPropriedadeHome() throws Exception {
    var response = mockRequest.perform(get(PATH_HELLO_ENDPOINT))
        .andExpect(status().isOk())
        .andReturn().getResponse();
    assertTrue(response.getContentAsString().contains("test"));
  }

  @Test
  @WithMockUser(username = "test_name")
  void deveriaRetornarNomeDoUsuarioAutenticado() throws Exception {
    var response = mockRequest.perform(get(PATH_HELLO_ENDPOINT))
        .andExpect(status().isOk())
        .andReturn().getResponse();
    assertTrue(response.getContentAsString().contains("test_name"));
  }

}
