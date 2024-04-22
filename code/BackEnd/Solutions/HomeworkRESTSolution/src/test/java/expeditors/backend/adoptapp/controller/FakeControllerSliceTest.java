package expeditors.backend.adoptapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.adoptapp.service.AdopterService;
import expeditors.backend.adoptapp.service.FakeService;
import expeditors.backend.utils.UriCreator;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@WebMvcTest(controllers = {FakeController.class})
@AutoConfigureMockMvc
public class FakeControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private FakeService fakeService;
//
//    @MockBean
//    private UriCreator uriCreator;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void init() {
       int stop = 0;
    }

    private List<Adopter> adopters = List.of(
          new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").breed("mixed").build()),

          new Adopter("Francine", "383 9339 9999 9393", LocalDate.of(2020, 5, 9),
                Pet.builder(PetType.DOG).name("slinky").breed("dalmation").build()),

          new Adopter("Darlene", "4484 9339 77939", LocalDate.of(2020, 5, 9),
                Pet.builder(PetType.TURTLE).name("swifty").build())
    );

    @Test
    public void testGetAll() throws Exception {
        MockHttpServletRequestBuilder builder = get("/fakeit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

    }
}
