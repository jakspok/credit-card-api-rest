package co.com.credit.service.api.controllers;

import co.com.credit.service.api.model.Card;
import co.com.credit.service.api.model.CardActivateRequest;
import co.com.credit.service.api.model.CardDailyLimitRequest;
import co.com.credit.service.api.model.CardDeactivateRequest;
import co.com.credit.service.api.services.ICardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/card")
@Api(tags = "Card")
public class CardController {

  @Autowired private ICardService cardService;

  @Autowired private ModelMapper modelMapper;

  @PutMapping(value = "card/enroll")
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

    Optional<Card> existCard = cardService.findById(request.getId());
    if (existCard != null) {

      // Check if card already deactivate
      if (existCard.getStatus().equals("1")) {
        HashMap res = new HashMap();
        res.put("status_code", 422);
        res.put("message", "Input invalid, Can' activate because int already activate");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
      }

      // process deactivate service
      existCard.setStatus(request.getStatus());
      if (cardService.activate(existCard.getId())) {
        HashMap res = new HashMap();
        res.put("data", existCard);
        res.put("message", "Operation, Activate  success");
        res.put("status_code", 200);
        return new ResponseEntity<>(res, HttpStatus.OK);
      }
    }

    HashMap res = new HashMap();
    res.put("status_code", 500);
    res.put("message", "Something went wrong.");
    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
  }

  @PutMapping(value = "card/{cardId}")
  @ApiResponses(
      value = { //
        @ApiResponse(code = 200, message = "Operation, Deactivate  success"), //
        @ApiResponse(code = 400, message = "Input invalid, Validation"), //
        @ApiResponse(
            code = 422,
            message = "Input invalid, Can' deactivate because int already deactivate "), //
        @ApiResponse(code = 500, message = "Something went wrong.")
      })
  public ResponseEntity<?> deactivate(
      @Valid @RequestBody CardDeactivateRequest request,
      BindingResult result) {
    // invalid input
    if (result.hasErrors()) {
      throw new ValidationException("Input card id not correct", (Throwable) result);
    }

    Optional<Card> existCard = cardService.findById(request.getId());
    if (existCard != null) {

      // Check if card already deactivate
      if (existCard.getStatus().equals("0")) {
        HashMap res = new HashMap();
        res.put("status_code", 422);
        res.put("message", "Input invalid, Can' deactivate because int already deactivate");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
      }

      // process deactivate service
      existCard.setStatus(inputCard.getStatus());
      if (cardService.deactivate(existCard.getId())) {
        HashMap res = new HashMap();
        res.put("data", existCard);
        res.put("message", "Operation, Deactivate  success");
        res.put("status_code", 200);
        return new ResponseEntity<>(res, HttpStatus.OK);
      }
    }

    HashMap res = new HashMap();
    res.put("status_code", 500);
    res.put("message", "Something went wrong.");
    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
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
    Card existCard = cardService.findActiveById(request.getId());
    if (existCard != null) {
      // set new daily limit
      existCard.setDailyLimit(request.getDailyLimit());
      if (cardService.changeDailyLimit(existCard)) {
        HashMap res = new HashMap();
        res.put("data", existCard);
        res.put("message", "Change dailyLimit Successful.");
        res.put("status_code", 200);
        return new ResponseEntity<>(res, HttpStatus.OK);
      }
    }

    HashMap res = new HashMap();
    res.put("status_code", 422);
    res.put("message", "Something went wrong.");
    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
  }
}
