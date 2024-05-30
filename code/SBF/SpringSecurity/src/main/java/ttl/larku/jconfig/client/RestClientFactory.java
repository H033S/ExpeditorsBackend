package ttl.larku.jconfig.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Base64;

@Component
public class RestClientFactory {
   @Autowired
   private ApplicationContext context;

   @Value("${spring.profiles.active}")
   private String profiles;

   public RestClient get(String baseUrl, String user, String pw) {
      //var restClient = profiles.contains("ssl") ? sslFromRestTemplate(baseUrl, user, pw)
      var restClient = profiles.contains("ssl") ? sslClient(baseUrl, user, pw)
            : basicAuth(baseUrl, user, pw);

      return restClient;
   }

   public RestClient noAuth(String baseUrl) {
      var restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();

      return restClient;
   }

   public RestClient basicAuth(String baseUrl, String user, String pw) {
      String basicAuthHeader = STR."basic \{Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes())}";

      var restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .build();

      return restClient;
   }

   public RestClient sslClientFromBundle(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = "basic " + Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes());

//      var sslRestTemplate = context.getBean("sslRestTemplate", RestTemplate.class);

      //var sslClientBuilder = context.getBean("sslRestClientBuilder", RestClient.Builder.class);
      var sslClientBuilder = context.getBean("bundledRestClient", RestClient.Builder.class);

      //This one will only work if we have put our self-signed
      //certificate into the 'cacerts' file for our jdk.
      //Look at the README.SSL file for more information.
//        var sslRestTemplate = context.getBean("bundledRestClient", RestTemplate.class);

      var restClient = sslClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .uriBuilderFactory(new DefaultUriBuilderFactory(baseUrl))
            .build();

      return restClient;
   }


   public RestClient sslClient(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = STR."basic \{Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes())}";

//      var sslRestTemplate = context.getBean("sslRestTemplate", RestTemplate.class);

      var sslClientBuilder = context.getBean("sslRestClientBuilder", RestClient.Builder.class);

      //This one will only work if we have put our self-signed
      //certificate into the 'cacerts' file for our jdk.
      //Look at the README.SSL file for more information.
//        var sslRestTemplate = context.getBean("bundledRestClient", RestTemplate.class);

      var restClient = sslClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .uriBuilderFactory(new DefaultUriBuilderFactory(baseUrl))
            .build();

      return restClient;
   }

   public RestClient sslFromRestTemplate(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = STR."basic \{Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes())}";

      var sslRestTemplate = context.getBean("sslRestTemplate", RestTemplate.class);

      //This is taking advantage of the fact that we have put our self signed
      //certificate into the 'cacerts' file for our jdk
//        var sslRestTemplate = context.getBean("bundledRestClient", RestTemplate.class);

      sslRestTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
      var restClient = RestClient.builder(sslRestTemplate)
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .build();

      return restClient;
   }
}