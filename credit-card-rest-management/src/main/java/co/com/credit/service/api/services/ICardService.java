package co.com.credit.service.api.services;


import co.com.credit.service.api.model.Card;
import co.com.credit.service.api.model.CardActivateRequest;
import co.com.credit.service.api.model.CardDailyLimitRequest;
import co.com.credit.service.api.model.CardDeactivateRequest;

import java.util.Optional;

public interface ICardService {

  Optional<Card> generateCard(Card card);

  Optional<Card> findById(Long cardId);

  Optional<Card> findActiveById(Long cardId);

  Optional<Card> changeDailyLimit(CardDailyLimitRequest cardDailyLimitRequest);

  Boolean activate(CardActivateRequest cardActivateRequest);

  Boolean deactivate(CardDeactivateRequest cardDeactivateRequest);

  Optional<Card> queryBalance(Long cardId);

  Optional<Card> queryTransactionPurchase(Long cardId);

  Optional<Card> queryTransaction(Long cardId);


}
