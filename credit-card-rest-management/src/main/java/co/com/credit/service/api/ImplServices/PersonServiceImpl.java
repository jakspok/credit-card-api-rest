package co.com.credit.service.api.ImplServices;

import co.com.credit.service.api.model.Person;
import co.com.credit.service.api.repositories.IPersonRepository;
import co.com.credit.service.api.services.IPersonService;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PersonServiceImpl implements IPersonService {

  private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);
  private final IPersonRepository repository;
  private static final String ERROR_CODE_ACTION_SAVE = "15";
  private static final String ERROR_CODE_ACTION_FIND = "16";
  private static final String SERVER_STATUS_CODE = "500";

  @Autowired
  public PersonServiceImpl(final IPersonRepository repository) {

    this.repository = repository;
  }

  @Override
  public Optional<Person> findByDocumentId(final String documentId) {

    return Try.of(() -> repository.findById(Integer.valueOf(documentId)))
        .onSuccess(person -> LOGGER.info("Returned person: {}", person))
        .onFailure(
            throwable -> {
              try {
                throw new Exception(
                    "Error saving person in BD : "
                        .concat(throwable.getMessage())
                        .concat(", person with document: ")
                        .concat(documentId),
                    throwable.getCause());
              } catch (final Exception e) {
                e.printStackTrace();
              }
            })
        .get();
  }

  @Override
  public Optional<Boolean> validEmail(final Person person) {

    final String regex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(person.getEmail());

    if (matcher.matches()) {
      return Optional.of(true);
    } else {
      return Optional.of(false);
    }
  }

  @Override
  public Optional<Person> savePerson(final Person person) {

    return Optional.ofNullable(
        Try.of(() -> repository.save(person))
            .onSuccess(persona -> LOGGER.info("Returned person: {}", persona))
            .onFailure(
                throwable -> {
                  try {
                    throw new Exception(
                        "Error saving person in BD : "
                            .concat(throwable.getMessage())
                            .concat(", person with document: ")
                            .concat(String.valueOf(person.getId())),
                        throwable.getCause());
                  } catch (final Exception e) {
                    e.printStackTrace();
                  }
                })
            .getOrNull());
  }

  @Override
  public Optional<Person> updatePerson(final Person person) {
    return Try.of(() -> repository.findById(person.getId()))
        .onFailure(
            throwable -> {
              try {
                throw new Exception(
                    "Error saving persona in BD : "
                        .concat(throwable.getMessage())
                        .concat(", persona with document: ")
                        .concat(String.valueOf(person.getId())),
                    throwable.getCause());
              } catch (final Exception e) {
                e.printStackTrace();
              }
            })
        .onSuccess(UpdateUser -> repository.save(person))
        .getOrNull();
  }

  @Override
  public Optional<Person> deletePerson(final String deletePersonId) {
    return Try.of(() -> repository.findById(Integer.valueOf(deletePersonId)))
        .onFailure(
            throwable -> {
              try {
                throw new Exception(
                    "Error deletePerson persona in BD : "
                        .concat(throwable.getMessage())
                        .concat(", persona with document: ")
                        .concat(String.valueOf(deletePersonId)),
                    throwable.getCause());
              } catch (final Exception e) {
                e.printStackTrace();
              }
            })
        .onSuccess(UpdateUser -> repository.deleteById(Integer.valueOf(deletePersonId)))
        .getOrNull();
  }

  @Override
  public List<Person> listPerson() {
    return (List<Person>)
        Try.of(() -> repository.findAll())
            .onFailure(
                throwable -> {
                  try {
                    throw new Exception(
                        "Error deletePerson persona in BD : "
                            .concat(throwable.getMessage())
                            .concat(", persona with document: "),
                        throwable.getCause());
                  } catch (final Exception e) {
                    e.printStackTrace();
                  }
                })
            .onSuccess(UpdateUser -> repository.findAll())
            .getOrNull();
  }
}
