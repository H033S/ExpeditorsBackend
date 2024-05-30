package org.ttl.ratingservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CourseRatingControllerMvcTest {

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testGetRatingForCourseWithoutCredentialsGive401() throws Exception {
      int id = 1;

      MockHttpServletRequestBuilder builder = get("/rating/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isUnauthorized());

//      MvcResult result = actions.andReturn();
//
//      double rating = Double.parseDouble(result.getResponse().getContentAsString());
//
//      System.out.println("Rating: " + rating);
//      assertTrue(rating > 0);
   }

   @Test
   @WithMockUser(roles = {"USER"})
   public void testGetLimitsWithValidUserGives200() throws Exception {

      var actions = mockMvc.perform(get("/admin/lowerLimit"))
            .andExpect(status().isOk()).andReturn();

      var ll = actions.getResponse().getContentAsString();

      actions = mockMvc.perform(get("/admin/upperLimit"))
            .andExpect(status().isOk()).andReturn();

      var ul = actions.getResponse().getContentAsString();

      System.out.println("ll: " + ll + ", ul: " + ul);
   }


   @Test
   @WithMockUser(roles = {"USER"})
   public void testSetLimitsWithInvalidUserGives403() throws Exception {

      var actions = mockMvc.perform(put("/admin/lowerLimit/{ll}", 5.0))
            .andExpect(status().isForbidden()).andReturn();

//      actions = mockMvc.perform(put("/admin/upperLimit/{ul}", 35.0))
//            .andExpect(status().isNoContent()).andReturn();
//
//      actions = mockMvc.perform(get("/admin/bothLimits"))
//                  .andExpect(status().isOk()).andReturn();
//
//      var result = actions.getResponse().getContentAsString();
//      System.out.println("result: " + result);
//
//      assertEquals("5.0:35.0", result);
   }

   @Test
   @WithMockUser(roles = {"ADMIN"})
   public void testSetLimitsWithUserGives200() throws Exception {

      var actions = mockMvc.perform(put("/admin/lowerLimit/{ll}", 5.0))
            .andExpect(status().isNoContent()).andReturn();

      actions = mockMvc.perform(put("/admin/upperLimit/{ul}", 35.0))
            .andExpect(status().isNoContent()).andReturn();

      actions = mockMvc.perform(get("/admin/bothLimits"))
                  .andExpect(status().isOk()).andReturn();

      var result = actions.getResponse().getContentAsString();
      System.out.println("result: " + result);

      assertEquals("5.0:35.0", result);
   }
}
