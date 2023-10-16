package co.com.credit.service.api.repositories;


import co.com.credit.service.api.model.Card;
import org.springframework.data.repository.CrudRepository;

public interface ICardRepository extends CrudRepository<Card, Long> {


}
