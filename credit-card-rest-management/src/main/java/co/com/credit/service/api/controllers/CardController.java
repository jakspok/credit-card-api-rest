package co.com.credit.service.api.controllers;

import co.com.credit.service.api.ImplServices.CardServiceImpl;
import co.com.credit.service.api.model.Card;
import co.com.credit.service.api.model.CardActivateRequest;
import co.com.credit.service.api.model.CardDailyLimitRequest;
import co.com.credit.service.api.services.ICardService;
import co.com.credit.service.api.services.IPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/card")
@Api(tags = "Card")
public class CardController {

  private static final Logger LOGGER = LogManager.getLogger(CardServiceImpl.class);

  @Autowired private ICardService cardService;

  @Autowired private IPersonService iPersonService;


  @PostMapping(value = "/number")
  @ApiResponses(
          value = { //
                  @ApiResponse(code = 200, message = "Generation, success"),
                  @ApiResponse(code = 400, message = "Input invalid, Validation"),
                  @ApiResponse(
                          code = 422,
                          message = "Input invalid, card generation"), //
                  @ApiResponse(code = 500, message = "Something went wrong.")
          })
  public ResponseEntity<?> generate(
          @Valid @RequestBody Card request,BindingResult result) {

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


  @PutMapping(value = "/enroll")
  @ApiResponses(
      value = { //
        @ApiResponse(code = 200, message = "Operation, Activate  success"),
        @ApiResponse(code = 400, message = "Input invalid, Validation"),
        @ApiResponse(
            code = 422,
            message = "Input invalid, Can' activate because int already activate "), //
        @ApiResponse(code = 500, message = "Something went wrong.")
      })
  public ResponseEntity<?> activate(
      @Valid @RequestBody CardActivateRequest request,
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

  @DeleteMapping(value = "/{cardId}")
  @ApiResponses(
      value = { //
        @ApiResponse(code = 200, message = "Operation, Blocked  success"), //
        @ApiResponse(code = 400, message = "Input invalid, Validation"), //
        @ApiResponse(
            code = 422,
            message = "Input invalid, Can' deactivate because int already deactivate "), //
        @ApiResponse(code = 500, message = "Something went wrong.")
      })
  public ResponseEntity<?> deactivate(
      @Valid @RequestBody Card request,
      BindingResult result) {
    // invalid input
    if (result.hasErrors()) {
      throw new ValidationException("Input card id not correct", (Throwable) result);
    }

    return Try.of(() -> cardService.deactivate(request))
            .onSuccess(card -> cardService.saveCard(request))
            .map(card -> new ResponseEntity<>(card, HttpStatus.OK))
            .onFailure(
                    throwable -> {
                      throw new ServiceException(
                              "Error en blocked card :", throwable.getCause());
                    })
            .get();
  }

  @PutMapping(value = "card/balance")
  @ApiResponses(
      value = { //
        @ApiResponse(code = 200, message = "Operation success"), //
        @ApiResponse(code = 400, message = "Input invalid, Validation"), //
        @ApiResponse(code = 422, message = "Input invalid, Business maximum"), //
        @ApiResponse(code = 500, message = "Something went wrong.")
      })
  public ResponseEntity<?> dailyLimit(
      @Valid @RequestBody CardDailyLimitRequest request,
      BindingResult result) {
    if (result.hasErrors()) {
      throw new ValidationException("Incorrect DailyLimit", (Throwable) result);
    }

    // find existing card
    Optional<Card> existCard = cardService.findActiveById(request.getId());
    if (existCard != null) {
      // set new daily limit
      existCard.get().setDailyLimit(request.getBalance());

    }

    HashMap res = new HashMap();
    res.put("status_code", 422);
    res.put("message", "Something went wrong.");
    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
  }
}
