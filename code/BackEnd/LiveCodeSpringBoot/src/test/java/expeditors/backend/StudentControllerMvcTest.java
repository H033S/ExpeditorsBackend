package expeditors.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class StudentControllerMvcTest {

   @Autowired
   private MockMvc mockMvc;

   private String baseUrl = "http://localhost:8080/student";

   @Test
   public void testGetAll() throws Exception {
      var actions = mockMvc.perform(
            get(baseUrl)
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
      ).andExpect(status().isOk());

      var result = actions.andReturn();
      var jsonResult = result.getResponse().getContentAsString();

      System.out.println("jsonResult: " + jsonResult);
   }
}
