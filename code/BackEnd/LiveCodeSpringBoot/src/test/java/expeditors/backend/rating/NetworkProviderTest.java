package expeditors.backend.rating;


import expeditors.backend.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * A Test of the Network Client using a MockRestServiceServer.
 */
@RestClientTest(NetworkRatingProvider.class)
@EnabledIf(expression = "#{environment.matchesProfiles('networkrating')}", loadContext = true)
public class NetworkProviderTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private NetworkRatingProvider provider;

    @Test
    public void testGetGoodRating() {
        String url = "http://localhost:10001/courseRating/1";
        this.server.expect(requestTo(url)).andRespond(withSuccess("1", MediaType.APPLICATION_JSON));
        Course course = new Course("Math-101", "Intro to Math");
        course.setId(1);

        provider.addRatingToCourse(course);
        assertTrue(course.getRating() > 0);
    }

}
