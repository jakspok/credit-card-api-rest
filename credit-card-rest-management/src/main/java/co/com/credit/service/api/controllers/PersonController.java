package co.com.credit.service.api.controllers;

import co.com.credit.service.api.model.Person;
import co.com.credit.service.api.services.IPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registro-persona")
@CrossOrigin(origins = "*")
public class PersonController {

  private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

  private final IPersonService personService;

  @Autowired
  public PersonController(final IPersonService personService) {
    this.personService = personService;
  }

  @GetMapping(
      value = "/person/{documentId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Person> findPerson(@PathVariable final String documentId) {

    LOGGER.info("Person documentId: {}", documentId);

    return personService
        .findByDocumentId(documentId)
        .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(
      value = "/savePerson",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity savePerson(@RequestBody final Person person) {

    LOGGER.info("Init save Person controller : {}\n body : {}", person);

    return (ResponseEntity)
        Try.of(() -> personService.validEmail(person))
            .onSuccess(persona -> personService.savePerson(person))
            .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
            .onFailure(
                throwable -> {
                  throw new ServiceException(
                      "Error en registro de persona Email no valido : ", throwable.getCause());
                });
  }

  @PutMapping(
      value = "/updatePerson",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity updatePerson(@RequestBody final Person person) {

    LOGGER.info("Init updatePerson Person controller : {}\n body : {}", person);

    return personService
        .updatePerson(person)
        .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping(
      value = "/deletePerson/{deletePersonId}",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity deletePerson(@PathVariable final String deletePersonId) {

    LOGGER.info("Init deletePerson Person controller : {}\n body : {}", deletePersonId);

    return personService
        .deletePerson(deletePersonId)
        .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(value = "/listPerson", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<ResponseEntity<Person>> listPerson() {

    LOGGER.info("Init listPerson controller : {}\n body : {}");

    return personService.listPerson().stream()
        .map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
        .collect(Collectors.toList());
  }
}
