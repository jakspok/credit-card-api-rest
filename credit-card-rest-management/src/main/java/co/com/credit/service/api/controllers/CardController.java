package co.com.credit.service.api.controllers;

import co.com.credit.service.api.ImplServices.CardServiceImpl;
import co.com.credit.service.api.model.Card;
import co.com.credit.service.api.model.Transaction;
import co.com.credit.service.api.repositories.ICardRepository;
import co.com.credit.service.api.services.ICardService;
import io.swagger.annotations.Api;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/v1/api/card")
@Api(tags = "Card")
@CrossOrigin(origins = "*")
public class CardController {

  private static final Logger LOGGER = LogManager.getLogger(CardServiceImpl.class);

  @Autowired
  private ICardService cardService;
  @Autowired
  private ICardRepository iCardRepository;

  @PostMapping(value = "/number",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> generate(
          @Valid @RequestBody Card request, BindingResult result) {

    // invalid input
    if (result.hasErrors()) {
      throw new ValidationException("Input card id not correct", (Throwable) result);
    }

    return
            Try.of(() -> cardService.generateCard(request))
                    .onSuccess(card -> cardService.saveCard(card.get()))
                    .map(card -> new ResponseEntity<>(card, HttpStatus.OK))
                    .onFailure(
                            throwable -> {
                              throw new ServiceException(
                                      "Error en activacion de tarjeta  : ", throwable.getCause());
                            })
                    .get();
  }


  @PutMapping(value = "/enroll",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> activate(
          @Valid @RequestBody Card request,
          BindingResult result) {

    // invalid input
    if (result.hasErrors()) {
      throw new ValidationException("Input card id not correct", (Throwable) result);
    }

    return Try.of(() -> cardService.findCard(request.getId()))
            .onSuccess(card -> cardService.saveCard(card.get()))
            .map(card -> new ResponseEntity<>(card, HttpStatus.OK))
            .onFailure(
                    throwable -> {
                      throw new ServiceException(
                              "Error en activacion de tarjeta  : ", throwable.getCause());
                    })
            .get();
  }

  @DeleteMapping("/{cardId}")
  @ResponseBody
  public ResponseEntity<String> deactivate(
          @PathVariable final Long cardId) {

    cardService.deactivate(cardId);

    return new ResponseEntity<String>(
            "Product with id : " + cardId +   " Eliminated ",HttpStatus.OK);

  }

  @PostMapping(value = "/balance",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> dailyLimit(
          @Valid @RequestBody Card card,
          BindingResult result) {
    if (result.hasErrors()) {
      throw new ValidationException("Incorrect DailyLimit", (Throwable) result);
    }

    return cardService
            .queryBalance(card)
            .map(cardBalance -> new ResponseEntity<>(card, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


  }

  @GetMapping(value = "/balance/{cardId}",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getBalance(
          @Valid @RequestBody Card card,
          BindingResult result) {
    if (result.hasErrors()) {
      throw new ValidationException("Incorrect Balance", (Throwable) result);
    }

    return cardService
            .queryBalance(card)
            .map(cardBalance -> new ResponseEntity<>(card, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

  @GetMapping(value = "/transaction/purchase",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getTransactionId(
          @Valid @RequestBody Transaction transaction,
          BindingResult result) {
    if (result.hasErrors()) {
      throw new ValidationException("Incorrect transaction", (Throwable) result);
    }

    return cardService
            .queryTransactionPurchase(transaction)
            .map(cardBalance -> new ResponseEntity<>(transaction, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


  }

}
