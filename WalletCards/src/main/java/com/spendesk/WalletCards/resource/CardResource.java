package com.spendesk.WalletCards.resource;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.mapper.CardMapper;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.CardStatus;
import com.spendesk.WalletCards.model.api.CardApiDto;
import com.spendesk.WalletCards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

// Peut être inutile si tout dans Wallet
@RestController
public class CardResource {

  private final CardService cardService;

  Logger logger = LoggerFactory.getLogger(CardResource.class);

  @Autowired
  public CardResource(CardService cardService) {
    this.cardService = cardService;
  }

  @PatchMapping("/cards/{cardId}/card-balance")
  public CardApiDto updateCardBalance(
      @RequestHeader(ResourceConstants.USER_HEADER) String userHeader,
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @PathVariable String cardId,
      @RequestBody BigDecimal balance)
      throws SpendeskException {
    return CardMapper.toApi(
        cardService.updateBalance(cardId, balance, new BusinessContext(userHeader, companyHeader)));
  }

  @PatchMapping("/cards/{cardId}/card-status")
  public CardApiDto updateCardStatus(
      @RequestHeader(ResourceConstants.USER_HEADER) String userHeader,
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @PathVariable String cardId,
      @RequestBody CardStatus cardStatus)
      throws SpendeskException {
    return CardMapper.toApi(
        cardService.updateStatus(
            cardId, cardStatus, new BusinessContext(userHeader, companyHeader)));
  }
}
