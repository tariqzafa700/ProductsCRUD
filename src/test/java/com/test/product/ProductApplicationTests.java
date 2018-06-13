package com.test.product;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.product.api.ResponseObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductConfig.class, ProductApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(
		  locations = "classpath:application.properties")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductApplicationTests {
    
    @LocalServerPort
	private int port;
    
    @Autowired
    private MockMvc mvc;
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void testFindAll() throws Exception {
        String product1 = "{\"name\": \"scooter\", \"quantity\": 10}";
        String product2 = "{\"name\": \"car\", \"quantity\": 15}";

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();

        mvc.perform(post(queryUrl).accept("application/json").content(product1).contentType(MediaType.APPLICATION_JSON_VALUE))                   
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("action", is("created")));
        mvc.perform(post(queryUrl).accept("application/json").content(product2).contentType(MediaType.APPLICATION_JSON_VALUE))                   
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("action", is("created")));
                //.andReturn();
      //String content = res.getResponse().getContentAsString();  // verify the response string.
      //System.out.println("content" + content);
      
        mvc.perform(get(queryUrl).accept("application/json"))                   
                  .andDo(print())
        	      .andExpect(status().isOk())
        	      .andExpect(jsonPath("$[1].name", is("car")));
    }
    
    @Test
    public void testUpdate() throws Exception {
        String product2 = "{\"name\": \"bike\", \"quantity\": 15}";

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();

        MvcResult res = mvc.perform(post(queryUrl).accept("application/json").content(product2).contentType(MediaType.APPLICATION_JSON_VALUE))                   
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
      String content = res.getResponse().getContentAsString();
      ResponseObject obj = mapper.readValue(content, ResponseObject.class);
      
      String product3 = "{\"name\": \"bike\", \"quantity\": 19}";
        mvc.perform(put(queryUrl+"/"+obj.getId()).accept("application/json").content(product3).contentType(MediaType.APPLICATION_JSON_VALUE))                
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("action", is("updated")));
    }
    
    @Test
    public void testDelete() throws Exception {
        String product2 = "{\"name\": \"bicycle\", \"quantity\": 15}";

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();

        MvcResult res = mvc.perform(post(queryUrl).accept("application/json").content(product2).contentType(MediaType.APPLICATION_JSON_VALUE))                   
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
      String content = res.getResponse().getContentAsString();
      ResponseObject obj = mapper.readValue(content, ResponseObject.class);
      
        mvc.perform(delete(queryUrl+"/"+obj.getId()).accept("application/json"))                
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("action", is("deleted")));
    }
    
    @Test
    public void testDeleteException() throws Exception {

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();
      
        mvc.perform(delete(queryUrl+"/"+100).accept("application/json"))                
                  .andDo(print())
                  .andExpect(status().is(500))
                  .andExpect(jsonPath("errorMessage", is("product with id 100 not found")));
    }
    
    @Test
    public void testUpdateException() throws Exception {
        String product2 = "{\"name\": \"truck\", \"quantity\": 15}";

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();
      
        mvc.perform(put(queryUrl+"/"+100).accept("application/json").content(product2).contentType(MediaType.APPLICATION_JSON_VALUE))                
                  .andDo(print())
                  .andExpect(status().is(500))
                  .andExpect(jsonPath("errorMessage", is("product with id 100 not found")));
    }
    
    @Test
    public void testCreateException() throws Exception {
        String product2 = "{\"name\": \"car\", \"quantity\": 15}";

        String queryUrl = UriComponentsBuilder.fromUriString("http://localhost:"+port)
                .path("/products")
                .toUriString().trim();
      
        mvc.perform(post(queryUrl).accept("application/json").content(product2).contentType(MediaType.APPLICATION_JSON_VALUE))                
                  .andDo(print())
                  .andExpect(status().is(500))
                  .andExpect(jsonPath("errorMessage", is("Duplicate product car")));
    }

	@Test
	public void contextLoads() {
	}

}
