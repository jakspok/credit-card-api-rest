package co.com.credit.service.api.services;


import co.com.credit.service.api.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {

  Optional<Person> findByDocumentId(String documentId);

  Optional<Boolean> validEmail(Person person);

  Optional<Person> savePerson(Person person);

  Optional<Person> updatePerson(Person person);

  Optional<Person> deletePerson(String deletePersonId);

  List<Person> listPerson();
}
