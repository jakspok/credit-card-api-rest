package co.com.credit.service.api.services;


import co.com.credit.service.api.model.*;

import java.util.Optional;

public interface ICardService {

  Optional<Card> generateCard(Card card);

  Optional<Card> saveCard(Card card);

  Optional<Card> findActiveById(Long cardId);

  Optional<Card> findCard(Long cardNumber);

  Optional<Card> changeDailyLimit(Card card);

  Boolean activate(Card card);

  Optional<Card> deactivate(Long deleteCard);

  Optional<Card> queryBalance(Card card);

  Optional<Card> queryBalanceById(Card card);

  Optional<Card> queryTransactionPurchase(Transaction transaction);

  Optional<Card> queryTransaction(Transaction transaction);


}
