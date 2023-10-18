package co.com.credit.service.api.ImplServices;

import co.com.credit.service.api.model.*;
import co.com.credit.service.api.repositories.ICardRepository;
import co.com.credit.service.api.services.ICardService;
import co.com.credit.service.api.util.numbersRandom;
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

    @Autowired
    public CardServiceImpl(final ICardRepository repository) {

        this.repository = repository;
    }

    @Override
    public Optional<Card> generateCard(Card card) {
        return
                Optional.ofNullable(Try.of(() -> {

                    TypeProduct numberCard = TypeProduct.valueOf(String.valueOf(card.getProductId()));

                    return Card.builder()
                            .cardNumber(Long.valueOf(numberCard.getId().concat(numbersRandom.generateRandom(10))))
                            .holderName(card.getHolderName())
                            .productId(numberCard)
                            .expiredDate(card.getExpiredDate())
                            .csv(numbersRandom.generateRandom(3))
                            .dailyLimit(0.0)
                            .status(Status.INACTIVE)
                            .build();

                })
                        .onSuccess(generateCard -> LOGGER.info("Returned card: {}", card))
                        .onFailure(throwable -> {
                            try {
                                throw new Exception(
                                        "Error saving card in BD : "
                                                .concat(throwable.getMessage().toString())
                                                .concat(", card with id: ")
                                                .concat(card.toString()),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .get());
    }


    @Override
    public Optional<Card> saveCard(Card card) {
        return Optional.ofNullable(
                Try.of(() -> repository.save(card))
                        .onSuccess(cardSave -> LOGGER.info("Returned card: {}", card))
                        .onFailure(
                                throwable -> {
                                    try {
                                        throw new Exception(
                                                "Error saving card in BD : "
                                                        .concat(throwable.getMessage())
                                                        .concat(", card with document: ")
                                                        .concat(String.valueOf(card.getId())),
                                                throwable.getCause());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                        .getOrNull());
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
    public Optional<Card> findCard(Long cardNumber) {
        return
                Try.of(() -> repository.findByCardNumber(cardNumber))
                        .onSuccess(card -> card.get().setStatus(Status.ACTIVE))
                        .onFailure(
                                throwable -> {
                                    try {
                                        throw new Exception(
                                                "Error saving card in BD : "
                                                        .concat(throwable.getMessage())
                                                        .concat(", card with id: ")
                                                        .concat(cardNumber.toString()),
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
                .onSuccess(UpdateBalance -> Card.builder().dailyLimit(cardDailyLimitRequest.getBalance()))
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

                .getOrNull();
    }

    @Override
    public Boolean activate(final CardActivateRequest cardActivateRequest) {
        return
                Try.of(() -> repository.findById(cardActivateRequest.getId()))
                        .onSuccess(generateCard -> generateCard.get().setStatus(Status.ACTIVE))
                        .onFailure(throwable -> {
                            try {
                                throw new Exception(
                                        "Error saving card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(cardActivateRequest.getId().toString()),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .get().isPresent();

    }

    @Override
    public Optional<Card> deactivate(final Long cardBlocked) {

        Optional<Card> card = repository.findByCardNumber(cardBlocked);

        return Try.of(() -> repository.findById(card.get().getId()))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error deletePerson persona in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", persona with document: ")
                                                .concat(String.valueOf(cardBlocked)),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(updateCard -> updateCard.get().setStatus(card.get().getStatus()))
                .getOrNull();

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
