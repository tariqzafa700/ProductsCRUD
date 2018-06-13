package com.test.product;

import java.util.List;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Profile("test")
@EnableWebMvc
class ProductConfig implements WebMvcConfigurer {
  @Bean
  ServletWebServerFactory servletWebServerFactory() {
      return new TomcatServletWebServerFactory();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(new MappingJackson2HttpMessageConverter());
  }

}