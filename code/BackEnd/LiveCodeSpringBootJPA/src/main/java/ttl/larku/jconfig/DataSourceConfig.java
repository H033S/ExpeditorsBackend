package ttl.larku.jconfig;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//@Configuration
//@Profile("prod")
//public class DataSourceConfig {
//
//   @Value("{DB_PASSWORD}")
//   private String pw;
//
//   @Bean
//   public DataSource dataSource() {
//      String url = "jdbc:postgresql://localhost:5433/larku";
//      String user = "larku";
//      String pw = System.getenv("DB_PASSWORD");
//      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);
//      return dataSource;
//   }
//}
