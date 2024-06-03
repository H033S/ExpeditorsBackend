package ttl.larku.rating;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ttl.larku.jconfig.client.ClientSSLConfig;

//@Component
@Profile("networkrating")
public class WhuffoIsThis implements RatingProvider {

   private RestClient restClient;

   ClientSSLConfig clientSSLConfig = new ClientSSLConfig();

   public WhuffoIsThis(@Value("${rating.provider.url}")
                                   String baseUrl,
                                   @Value("${CLIENT_PASSWORD}")
                                   String password) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

      var builder = clientSSLConfig.sslRestClientBuilder(password);
//
//      this.restClient = RestClient.builder()
      this.restClient = builder
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Override
   public BigDecimal getRating(int id) {
      return getRatingFromService(id, null);
   }

   @Override
   public BigDecimal getRating(int id, String user, String pw) {
      var encoded = Base64.getEncoder().encodeToString((user + ":" + pw).getBytes());
      String basicAuthHeader = "Basic " + encoded;
      var result = getRatingFromService(id, basicAuthHeader);
      return result;
   }

   private BigDecimal getRatingFromService(int id, String authHeader) {
      var spec = restClient.get()
            .uri("/{id}", id);
      if (authHeader != null) {
         spec = spec.header("Authorization", authHeader);
      }
      var response = spec.retrieve()
            .toEntity(BigDecimal.class);

      if (response.getStatusCode() == HttpStatus.OK) {
         var rating = response.getBody();
         return rating;
      }
      return null;
   }
}
