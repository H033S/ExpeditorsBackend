package org.ttl.courserating.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CourseRatingControllerMvcTest {

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testGetRating() throws Exception {
      int id = 1;

      MockHttpServletRequestBuilder builder = get("/courseRating/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk());

      MvcResult result = actions.andReturn();

      String  rating = result.getResponse().getContentAsString();
      System.out.println("rating: " + rating);
   }
}
