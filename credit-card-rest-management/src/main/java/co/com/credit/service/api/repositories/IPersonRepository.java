package co.com.credit.service.api.repositories;


import co.com.credit.service.api.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonRepository extends CrudRepository<Person, Integer> {}
