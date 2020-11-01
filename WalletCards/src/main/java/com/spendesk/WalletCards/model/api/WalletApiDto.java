package com.spendesk.WalletCards.model.api;

import java.math.BigDecimal;

public class WalletApiDto {

  private BigDecimal balance;

  private String companyIdentifier;

  private String currency;

  private String id;

  private Boolean masterWallet;

  public WalletApiDto() {}

  public String getCurrency() {
    return currency;
  }

  public WalletApiDto setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public String getCompanyIdentifier() {
    return companyIdentifier;
  }

  public WalletApiDto setCompanyIdentifier(String companyIdentifier) {
    this.companyIdentifier = companyIdentifier;
    return this;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public WalletApiDto setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  public String getId() {
    return id;
  }

  public WalletApiDto setId(String id) {
    this.id = id;
    return this;
  }

  public Boolean isMasterWallet() {
    return masterWallet;
  }

  public WalletApiDto setMasterWallet(Boolean masterWallet) {
    this.masterWallet = masterWallet;
    return this;
  }
}
