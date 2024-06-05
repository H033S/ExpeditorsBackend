package ttl.larku.controllers.ssl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.web.client.RestClient;
import ttl.larku.controllers.rest.RestResultWrapper;
import ttl.larku.controllers.rest.RestResultWrapper.Status;
import ttl.larku.domain.Student;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("expensive")
@EnabledIf(expression = "#{environment.matchesProfiles('ssltest')}", loadContext = true)
public class RestClientSSLTest extends SqlScriptBase {

    @LocalServerPort
    private int port; // = 8443;

    @Autowired
    private ObjectMapper mapper;

    private RestClient restClient;

    @Value("${CLIENT_PASSWORD}")
    private String password;

    private String baseUrl;
    private String rootUrl;
    private String oneStudentUrl;

    RestClient ratingClient;

    @PostConstruct
    public void init() {
        baseUrl = "https://localhost:10043/rating";

        String authHeader = "Basic " + Base64.getEncoder()
              .encodeToString("bobby:password".getBytes());

        this.restClient = RestClient.builder()
              .baseUrl(baseUrl)
              .defaultHeader("Accept", "application/json")
              .defaultHeader("Content-Type", "application/json")
              .defaultHeader("Authorization", authHeader)
              .build();
    }


    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCallCourseRatingServiceRestClientAndStraighHttpsURL() {
        var myRatingClient= this.restClient;
//        var myRatingClient= clientFactory.sslClientFromRestClient("https://localhost:10043/rating", "bobby", password);
//        var myRatingClient= clientFactory.sslFromRestTemplate("https://localhost:10043/rating", "bobby", password);
        var response = myRatingClient.get()
              .uri("/{id}", 2)
              .retrieve()
              .toEntity(BigDecimal.class);

        var result = response.getBody();

        System.out.println("Result: " + result);


        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
