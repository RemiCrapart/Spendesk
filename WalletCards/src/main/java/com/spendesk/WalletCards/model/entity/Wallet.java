package com.spendesk.WalletCards.model.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Wallet {

  private BigDecimal balance;

  private String companyIdentifier;

  private String currency;

  @Id private ObjectId id;

  private boolean masterWallet;

  public BigDecimal getBalance() {
    return balance;
  }

  public Wallet setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public Wallet setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public String getCompanyIdentifier() {
    return companyIdentifier;
  }

  public Wallet setCompanyIdentifier(String companyIdentifier) {
    this.companyIdentifier = companyIdentifier;
    return this;
  }

  public ObjectId getId() {
    return id;
  }

  public Wallet setId(ObjectId id) {
    this.id = id;
    return this;
  }

  public boolean isMasterWallet() {
    return masterWallet;
  }

  public Wallet setMasterWallet(boolean masterWallet) {
    this.masterWallet = masterWallet;
    return this;
  }
}
