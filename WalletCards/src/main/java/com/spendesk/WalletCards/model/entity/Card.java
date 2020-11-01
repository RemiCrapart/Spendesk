package com.spendesk.WalletCards.model.entity;

import com.spendesk.WalletCards.model.CardStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
public class Card {

  // 3 digit
  private String CCV;

  private BigDecimal balance;

  // 16 digit
  private String cardPinNumber;

  private String currency;

  private LocalDate expirationDate;

  @Id private ObjectId id;

  private CardStatus status;

  private String userIdentifier;

  private String walletIdentifier;

  public BigDecimal getBalance() {
    return balance;
  }

  public Card setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public Card setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public String getCCV() {
    return CCV;
  }

  public Card setCCV(String CCV) {
    this.CCV = CCV;
    return this;
  }

  public String getCardPinNumber() {
    return cardPinNumber;
  }

  public Card setCardPinNumber(String cardPinNumber) {
    this.cardPinNumber = cardPinNumber;
    return this;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public Card setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  public ObjectId getId() {
    return id;
  }

  public Card setId(ObjectId id) {
    this.id = id;
    return this;
  }

  public CardStatus getStatus() {
    return status;
  }

  public Card setStatus(CardStatus status) {
    this.status = status;
    return this;
  }

  public String getUserIdentifier() {
    return userIdentifier;
  }

  public Card setUserIdentifier(String userIdentifier) {
    this.userIdentifier = userIdentifier;
    return this;
  }

  public String getWalletIdentifier() {
    return walletIdentifier;
  }

  public Card setWalletIdentifier(String walletIdentifier) {
    this.walletIdentifier = walletIdentifier;
    return this;
  }
}
