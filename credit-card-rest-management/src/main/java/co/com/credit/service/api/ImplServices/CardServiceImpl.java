package co.com.credit.service.api.ImplServices;

import co.com.credit.service.api.model.Card;
import co.com.credit.service.api.model.CardActivateRequest;
import co.com.credit.service.api.model.CardDailyLimitRequest;
import co.com.credit.service.api.model.CardDeactivateRequest;
import co.com.credit.service.api.repositories.ICardRepository;
import co.com.credit.service.api.services.ICardService;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements ICardService {

  private static final Logger LOGGER = LogManager.getLogger(CardServiceImpl.class);
  private final ICardRepository repository;
  private static final String ERROR_CODE_ACTION_SAVE = "15";
  private static final String ERROR_CODE_ACTION_FIND = "16";
  private static final String SERVER_STATUS_CODE = "500";

  @Autowired
  public CardServiceImpl(final ICardRepository repository) {

    this.repository = repository;
  }

    @Override
    public Optional<Card> generateCard(Card card) {
        return Try.of(() -> {

            int max= (int) (Math.pow(10,6)-1);
            int min= (int) Math.pow(10,6-1);
            int range = max - min + 1;
            int randomNumber = (int) (Math.random() * range) + min;

        })
                .onSuccess(cardGenerated -> Card.builder()
                        .id(card.getId())
                        .cardNumber(card.getId().concat(randomNumber))
                        .corrId(card.getCorrId())
                        .holderName(card.getHolderName())
                        .cardType(card.getCardType)
                        .expiredDate(card.expiredDate())
                        .csv(card.getCsv())
                        .dailyLimit(card.getDailyLimit())
                        .status(card.getStatus())
                        .build())
                .onFailure(throwable -> {
                    try {
                        throw new Exception(
                                "Error saving card in BD : "
                                        .concat(throwable.getMessage())
                                        .concat(", card with id: ")
                                        .concat(cardId.toString()),
                                throwable.getCause());
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                })
                .get();
    }

    @Override
  public Optional<Card> findById(final Long cardId) {

      return Try.of(() -> repository.findById(cardId))
              .onSuccess(card -> LOGGER.info("Returned Card: {}", card))
              .onFailure(
                      throwable -> {
                          try {
                              throw new Exception(
                                      "Error saving card in BD : "
                                              .concat(throwable.getMessage())
                                              .concat(", card with id: ")
                                              .concat(cardId.toString()),
                                      throwable.getCause());
                          } catch (final Exception e) {
                              e.printStackTrace();
                          }
                      })
              .get();
  }

  @Override
  public Optional<Card> findActiveById(final Long activateId) {

    return
        Try.of(() -> repository.findById(activateId))
            .onSuccess(card -> LOGGER.info("Returned Activated card: {}", card))
            .onFailure(
                throwable -> {
                  try {
                    throw new Exception(
                        "Error saving card in BD : "
                            .concat(throwable.getMessage())
                            .concat(", card with id: ")
                            .concat(activateId.toString()),
                        throwable.getCause());
                  } catch (final Exception e) {
                    e.printStackTrace();
                  }
                })
            .get();
  }

  @Override
  public Optional<Card> changeDailyLimit(final CardDailyLimitRequest cardDailyLimitRequest) {
    return Try.of(() -> repository.findById(cardDailyLimitRequest.getId()))
        .onFailure(
            throwable -> {
              try {
                throw new Exception(
                    "Error saving card in BD : "
                        .concat(throwable.getMessage())
                        .concat(", card with id: ")
                        .concat(String.valueOf(cardDailyLimitRequest)),
                    throwable.getCause());
              } catch (final Exception e) {
                e.printStackTrace();
              }
            })
            .onSuccess(UpdateBalance -> repository.save(cardActivateRequest))
            .getOrNull();
  }

      @Override
      public Boolean activate(final CardActivateRequest cardActivateRequest) {
          return Try.of(() -> repository.findById(cardActivateRequest.getId()))
                  .onFailure(
                          throwable -> {
                              try {
                                  throw new Exception(
                                          "Error delete card in BD : "
                                                  .concat(throwable.getMessage())
                                                  .concat(", card with id: ")
                                                  .concat(String.valueOf(cardActivateRequest)),
                                          throwable.getCause());
                              } catch (final Exception e) {
                                  e.printStackTrace();
                              }
                          })
                  .onSuccess(UpdateUser -> repository.deleteById(cardActivateRequest.getId()))
                  .get().isPresent();
      }

    @Override
      public Boolean deactivate(final CardDeactivateRequest cardDeactivateRequest) {
        return Try.of(() -> repository.findById(cardDeactivateRequest.getId()))
            .onFailure(
                throwable -> {
                  try {
                    throw new Exception(
                        "Error delete card in BD : "
                            .concat(throwable.getMessage())
                            .concat(", card with id: ")
                            .concat(String.valueOf(cardDeactivateRequest)),
                        throwable.getCause());
                  } catch (final Exception e) {
                    e.printStackTrace();
                  }
                })
            .onSuccess(updateCard -> repository.deleteById(cardDeactivateRequest.getId()))
            .get().isPresent();
  }

    @Override
    public Optional<Card> queryBalance(Long cardId) {
        return Try.of(() -> repository.findById(cardId))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error delete card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(String.valueOf(cardId)),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(card -> LOGGER.info("Returned Card: {}", card))
                .getOrNull();
    }

    @Override
    public Optional<Card> queryTransactionPurchase(Long cardId) {
        return Try.of(() -> repository.findById(cardId))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error query card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(String.valueOf(cardId)),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(card -> LOGGER.info("Returned Card: {}", card))
                .getOrNull();
    }

    @Override
    public Optional<Card> queryTransaction(Long cardId) {
        return Try.of(() -> repository.findById(cardId))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error query card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(String.valueOf(cardId)),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(card -> LOGGER.info("Returned Card: {}", card))
                .getOrNull();
    }

}
