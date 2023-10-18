package co.com.credit.service.api.repositories;


import co.com.credit.service.api.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICardRepository extends CrudRepository<Card, Long> {

    Optional<Card> findByCardNumber(Long cardNumber);


}
