package expeditors.backend.adoptapp;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

@SpringBootApplication
public class AdopterSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(AdopterSpringBootApp.class, args);
    }
}

@Component
class MyRunner implements CommandLineRunner
{

    @Autowired
    private AdopterService adopterService;

    @Override
    public void run(String... args) throws Exception {
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopterService.addAdopter(adopter);

        List<Adopter> result = adopterService.getAllAdopters();
        out.println("result: " + result.size());
        result.forEach(out::println);

    }
}
