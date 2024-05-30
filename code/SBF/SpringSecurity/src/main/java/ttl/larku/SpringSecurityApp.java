package ttl.larku;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
//This one is to get our WebFilter scanned
@ServletComponentScan
public class SpringSecurityApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(SpringSecurityApp.class);

        springApp.run(args);
    }

    @Override
    public void run(String... args) {
        System.out.println("SpringSecurityApp.Runner: init completed");
    }
}

