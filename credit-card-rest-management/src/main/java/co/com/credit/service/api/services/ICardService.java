package co.com.credit.service.api.services;


import co.com.credit.service.api.model.*;

import java.util.Optional;

public interface ICardService {

  Optional<Card> generateCard(Card card);

  Optional<Card> saveCard(Card card);

  Optional<Card> findActiveById(Long cardId);

  Optional<Card> findCard(Long cardNumber);

  Optional<Card> changeDailyLimit(CardDailyLimitRequest cardDailyLimitRequest);

  Boolean activate(CardActivateRequest cardActivateRequest);

  Optional<Card> deactivate(Long deleteCard);

  Optional<Card> queryBalance(Long cardId);

  Optional<Card> queryTransactionPurchase(Long cardId);

  Optional<Card> queryTransaction(Long cardId);


}
