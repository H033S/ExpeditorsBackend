package ttl.larku.jconfig.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * To configure the server to use SSL:
 * 1) Make a self signed certificate.  Important - the CN field
 * where it asks you for first and last name, should be set to
 * the hostname, e.g. localhost.
 * keytool -genkeypair -alias larkuspring
 * -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore larkUKeyfile.p12
 * <p>
 * 2) Make sure you point to this file with the appropriate properties in
 * application.[properties|yml]
 * Look at application-ssl.properties
 *
 * <p>
 * To configure RestTemplate to use SSL see the 'sslRestTemplate' method below.
 */
@Configuration
@Profile("ssl | ssltest")
public class ClientSSLConfig {

   private SslBundles sslBundles;
   private SslBundle ourBundle;

   public ClientSSLConfig(SslBundles sslBundles) {
      this.sslBundles = sslBundles;
      this.ourBundle = sslBundles.getBundle("web-server");
      ourBundle.getManagers().getTrustManagers()[0] = myTrustManager[0];
   }

   @Bean("bundledRestTemplate")
   public RestTemplate bundleSSLTemplateThatDoesNotWork() {
      var rt = new RestTemplateBuilder()
            .setSslBundle(sslBundles.getBundle("web-server"))
            .build();

      return rt;
   }

   @Bean("bundledRestClient")
   public RestClient.Builder bundleRestClient(RestClientSsl restClientSsl) {
      var rt = RestClient.builder()
            .apply(restClientSsl.fromBundle("web-server"));

      return rt;
   }

   /**
    * This one allows self-signed certificates
    */
   @Bean("sslRestTemplate")
   public RestTemplate sslRestTemplate(@Value("${CLIENT_PASSWORD}") String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
      KeyStore clientStore = KeyStore.getInstance("PKCS12");
      //We point it at the same keystore as the server
      clientStore.load(getClass().getResourceAsStream("larkUKeyfile.p12"), password.toCharArray());

      TrustStrategy lam = (X509Certificate[] chain, String authType) -> true;
      SSLContext sslContext = SSLContexts.custom()
            //.loadTrustMaterial(null, acceptingTrustStrategy)
            .loadKeyMaterial(clientStore, password.toCharArray())
            .loadTrustMaterial(new TrustSelfSignedStrategy())
            .build();

      SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
      HttpClientConnectionManager cm =
            PoolingHttpClientConnectionManagerBuilder.create()
                  .setSSLSocketFactory(sslSocketFactory).build();

      CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

      HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

      requestFactory.setHttpClient(httpClient);
      requestFactory.setConnectTimeout(10000); // 10 seconds

      RestTemplate restTemplate = new RestTemplateBuilder()
            .requestFactory(() -> requestFactory)
            .build();

      return restTemplate;
   }

   @Bean
   public RestClient.Builder sslRestClientBuilder(@Value("${CLIENT_PASSWORD}") String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
//      public RestClient.Builder sslRestClient(@Value("${CLIENT_PASSWORD}") String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
      KeyStore clientStore = KeyStore.getInstance("PKCS12");
      //We point it at the same keystore as the server
      clientStore.load(getClass().getResourceAsStream("larkUKeyfile.p12"), password.toCharArray());

      TrustStrategy lam = (X509Certificate[] chain, String authType) -> true;
      SSLContext sslContext = SSLContexts.custom()
            //.loadTrustMaterial(null, acceptingTrustStrategy)
            .loadKeyMaterial(clientStore, password.toCharArray())
            .loadTrustMaterial(new TrustSelfSignedStrategy())
            .build();

      SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
      HttpClientConnectionManager cm =
            PoolingHttpClientConnectionManagerBuilder.create()
                  .setSSLSocketFactory(sslSocketFactory).build();

      CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

      HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

      requestFactory.setHttpClient(httpClient);
      requestFactory.setConnectTimeout(10000); // 10 seconds

      var restClient = RestClient.builder()
            .requestFactory(requestFactory);
//            .build();

      return restClient;
   }

   TrustManager[] myTrustManager = new TrustManager[]{new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
      }

      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
      }
   }};

   public static class TrustAllHostNameVerifier implements HostnameVerifier {

      public boolean verify(String hostname, SSLSession session) {
         return true;
      }

   }
}