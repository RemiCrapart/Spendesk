package com.spendesk.WalletCards.model.client;

import java.math.BigDecimal;
import java.util.Map;

public class FixerRatesClientDto {
  public Map<String, BigDecimal> rates;

  public boolean success;
  public int timestamp;
  public String base;
  public String date;

  public Map<String, BigDecimal> getRates() {
    return rates;
  }

  public FixerRatesClientDto setRates(Map<String, BigDecimal> rates) {
    this.rates = rates;
    return this;
  }

  public boolean isSuccess() {
    return success;
  }

  public FixerRatesClientDto setSuccess(boolean success) {
    this.success = success;
    return this;
  }

  public int getTimestamp() {
    return timestamp;
  }

  public FixerRatesClientDto setTimestamp(int timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public String getBase() {
    return base;
  }

  public FixerRatesClientDto setBase(String base) {
    this.base = base;
    return this;
  }

  public String getDate() {
    return date;
  }

  public FixerRatesClientDto setDate(String date) {
    this.date = date;
    return this;
  }
}
