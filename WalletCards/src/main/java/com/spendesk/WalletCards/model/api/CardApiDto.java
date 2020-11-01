package com.spendesk.WalletCards.model.api;

import com.spendesk.WalletCards.model.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CardApiDto {

  private String CCV;

  private BigDecimal balance;

  private String cardPinNumber;

  private String currency;

  private LocalDate expirationDate;

  private String id;

  private CardStatus status;

  private String userIdentifier;

  private String walletIdentifier;

  public CardApiDto() {}

  public String getCurrency() {
    return currency;
  }

  public CardApiDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public CardApiDto setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  public String getCCV() {
    return CCV;
  }

  public CardApiDto setCCV(String CCV) {
    this.CCV = CCV;
    return this;
  }

  public String getCardPinNumber() {
    return cardPinNumber;
  }

  public CardApiDto setCardPinNumber(String cardPinNumber) {
    this.cardPinNumber = cardPinNumber;
    return this;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public CardApiDto setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  public String getId() {
    return id;
  }

  public CardApiDto setId(String id) {
    this.id = id;
    return this;
  }

  public CardStatus getStatus() {
    return status;
  }

  public CardApiDto setStatus(CardStatus status) {
    this.status = status;
    return this;
  }

  public String getUserIdentifier() {
    return userIdentifier;
  }

  public CardApiDto setUserIdentifier(String userIdentifier) {
    this.userIdentifier = userIdentifier;
    return this;
  }

  public String getWalletIdentifier() {
    return walletIdentifier;
  }

  public CardApiDto setWalletIdentifier(String walletIdentifier) {
    this.walletIdentifier = walletIdentifier;
    return this;
  }
}
