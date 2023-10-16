package co.com.person.service.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class PersonServiceApplication {

  public static void main(final String[] args) {

    SpringApplication.run(PersonServiceApplication.class, args);
  }
}
