package com.spendesk.WalletCards.model.api;

import com.spendesk.WalletCards.model.Currency;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateWalletApiDto {

  @NotNull private BigDecimal balance;

  @NotNull private Currency currency;

  public CreateWalletApiDto() {}

  public BigDecimal getBalance() {
    return balance;
  }

  public CreateWalletApiDto setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  public Currency getCurrency() {
    return currency;
  }

  public CreateWalletApiDto setCurrency(Currency currency) {
    this.currency = currency;
    return this;
  }
}
