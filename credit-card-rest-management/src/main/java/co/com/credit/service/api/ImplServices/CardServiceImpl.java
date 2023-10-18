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
    public Optional<Card> changeDailyLimit(final Card card) {

        Card cardDaily = repository.findByCardNumber(card.getCardNumber()).get();

        return Try.of(() -> repository.findById(card.getId()))
                .onSuccess(UpdateBalance -> cardDaily.setDailyLimit((card.getDailyLimit())))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error updating balance card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(String.valueOf(card)),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .getOrNull();
    }

    @Override
    public Boolean activate(final Card card) {
        return
                Try.of(() -> repository.findById(card.getId()))
                        .onSuccess(generateCard -> generateCard.get().setStatus(Status.ACTIVE))
                        .onFailure(throwable -> {
                            try {
                                throw new Exception(
                                        "Error saving card in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", card with id: ")
                                                .concat(card.getId().toString()),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .get().isPresent();
    }

    @Override
    public Optional<Card> deactivate(final Long cardBlocked) {

        return Try.of(() -> repository.findById(cardBlocked))
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
                .onSuccess(updateCard -> repository.deleteById(cardBlocked))
                .getOrNull();

    }

    @Override
    public Optional<Card> queryBalance(Card card) {
        return
                Try.of(() -> repository.findByCardNumber(card.getCardNumber()))
                        .onSuccess(cardBalance -> cardBalance.get().setDailyLimit(card.getDailyLimit()))
                        .onFailure(
                                throwable -> {
                                    try {
                                        throw new Exception(
                                                "Error query balance card in BD : "
                                                        .concat(throwable.getMessage())
                                                        .concat(", card with id: ")
                                                        .concat(card.toString()),
                                                throwable.getCause());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                        .get();
    }

    @Override
    public Optional<Card> queryBalanceById(Card card) {
        return
                Try.of(() -> repository.findById(card.getId()))
                        .onSuccess(cardBalance -> repository.save(card))
                        .onFailure(
                                throwable -> {
                                    try {
                                        throw new Exception(
                                                "Error query balance card in BD : "
                                                        .concat(throwable.getMessage())
                                                        .concat(", card with id: ")
                                                        .concat(card.toString()),
                                                throwable.getCause());
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                        .get();
    }

    @Override
    public Optional<Card> queryTransactionPurchase(Transaction transaction) {
        return Try.of(() -> repository.findById(transaction.getId()))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error transaction in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", transaction with id: ")
                                                .concat(String.valueOf(transaction.getId())),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(card -> LOGGER.info("Returned transaction: {}", card))
                .getOrNull();
    }

    @Override
    public Optional<Card> queryTransaction(Transaction transaction) {
        return Try.of(() -> repository.findById(transaction.getId()))
                .onFailure(
                        throwable -> {
                            try {
                                throw new Exception(
                                        "Error transaction in BD : "
                                                .concat(throwable.getMessage())
                                                .concat(", transaction with id: ")
                                                .concat(String.valueOf(transaction.getId())),
                                        throwable.getCause());
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        })
                .onSuccess(card -> LOGGER.info("Returned transaction: {}", card))
                .getOrNull();
    }

}
