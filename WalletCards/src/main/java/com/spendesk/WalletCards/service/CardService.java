package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.CardStatus;
import com.spendesk.WalletCards.model.entity.Card;
import com.spendesk.WalletCards.model.entity.Wallet;
import com.spendesk.WalletCards.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class CardService {

  Logger logger = LoggerFactory.getLogger(CardService.class);

  private CardRepository cardRepository;

  private WalletService walletService;

  @Autowired
  public CardService(CardRepository cardRepository, WalletService walletService) {
    this.cardRepository = cardRepository;
    this.walletService = walletService;
  }

  public Card create(String walletId, BusinessContext businessContext) throws Exception {

    // Retrieve wallet to get back the currency
    Wallet wallet = walletService.findById(walletId);

    walletService.checkAccess(wallet, businessContext);

    return cardRepository.save(initCard(wallet, businessContext.getUserId()));
  }

  public Card updateBalance(String cardId, BigDecimal amount, BusinessContext businessContext)
      throws SpendeskException {

    Card card = this.findById(cardId);
    checkCardAccess(card, businessContext);
    walletService.checkAccess(walletService.findById(card.getWalletIdentifier()), businessContext);
    return this.updateBalance(card, amount, businessContext);
  }

  public List<Card> findByWalletIdentifier(String walletId, BusinessContext businessContext)
      throws SpendeskException {
    Wallet wallet = walletService.findById(walletId);
    walletService.checkAccess(wallet, businessContext);

    return cardRepository.findByWalletIdentifier(walletId);
  }

  public Card findById(String cardId) throws SpendeskException {
    Optional card = cardRepository.findById(cardId);
    if (!card.isPresent()) {
      throw new SpendeskException(
          "This card id doesn't not exit", "CARDBUS01", HttpStatus.NOT_FOUND);
    }
    return cardRepository.findById(cardId).get();
  }

  public Card updateStatus(String cardId, CardStatus newCardStatus, BusinessContext businessContext)
      throws SpendeskException {
    Card card = findById(cardId);
    checkCardAccess(card, businessContext);
    walletService.checkAccess(walletService.findById(card.getWalletIdentifier()), businessContext);

    // If new status is block and card not already blocked, money should be transfer to wallet
    if (card.getStatus() != CardStatus.BLOCK && newCardStatus == CardStatus.BLOCK) {

      // add card balance to wallet balance
      BigDecimal amountToAddToWallet = card.getBalance();
      walletService.updateBalance(card.getWalletIdentifier(), amountToAddToWallet, businessContext);

      // set card balance to 0
      card.setBalance(new BigDecimal(0));
    }

    // update status
    // TODO save inutile dans la DB quand le status ne change pas ...
    card.setStatus(newCardStatus);

    return cardRepository.save(card);
  }

  /*
     A card access is define by the userIdentifier
  */
  private void checkCardAccess(Card card, BusinessContext businessContext)
      throws SpendeskException {
    if (!card.getUserIdentifier().equals(businessContext.getUserId())) {
      throw new SpendeskException(
          "This card doesn't belong to this user", "CARDSEC02", HttpStatus.NOT_FOUND);
    }
  }

  private Card updateBalance(Card card, BigDecimal amount, BusinessContext businessContext)
      throws SpendeskException {

    // Update only allowed if the card status is ACTIVE
    if (card.getStatus() == CardStatus.ACTIVE) {

      // Check card balance will not be negative
      BigDecimal currentBalance = card.getBalance();
      BigDecimal newBalance = currentBalance.add(amount);
      if (newBalance.signum() < 0) {
        throw new SpendeskException(
            "There is not enough money on the card", "CARDBUS02", HttpStatus.BAD_REQUEST);
      }

      // update wallet balance with the negation of the amount
      walletService.updateBalance(card.getWalletIdentifier(), amount.negate(), businessContext);

      // update card balance
      card.setBalance(newBalance);

      logger.info(
          "User id [{}] of Company [{}] update balance card [{}] for [{}] {} as amount",
          businessContext.getUserId(),
          businessContext.getCompanyId(),
          card.getId().toString(),
          amount,
          card.getCurrency());

      return cardRepository.save(card);

    } else {
      throw new SpendeskException("The card is not active", "CARDBUS02", HttpStatus.BAD_REQUEST);
    }
  }

  /*
  Generate random number of numberOfDigit size
   */
  private String generateRandomNumber(int numberOfDigit) {
    String randomNumber = "";
    Random random = new Random();
    for (int i = 0; i < numberOfDigit; i++) {
      randomNumber += random.nextInt(10);
    }
    return randomNumber;
  }

  private Card initCard(Wallet wallet, String userId) {

    return new Card()
        .setWalletIdentifier(wallet.getId().toString())
        .setCardPinNumber(generateRandomNumber(16))
        .setCCV(generateRandomNumber(3))
        .setExpirationDate(LocalDate.now().plusMonths(1))
        .setStatus(CardStatus.ACTIVE)
        .setUserIdentifier(userId)
        .setCurrency(wallet.getCurrency())
        .setBalance(new BigDecimal(0));
  }
}
