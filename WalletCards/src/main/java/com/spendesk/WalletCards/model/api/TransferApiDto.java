package com.spendesk.WalletCards.model.api;

import com.spendesk.WalletCards.model.EntityType;

import java.math.BigDecimal;

public class TransferApiDto {

  private BigDecimal amountTransferred;

  private BigDecimal conversionFee;

  private String id;

  private String originCurrency;

  private String originEntityIdentifier;

  private EntityType originEntityType;

  private String targetCurrency;

  private String targetEntityIdentifier;

  private EntityType targetEntityType;

  private String timestamp;

  private String userIdentifier;

  public TransferApiDto() {}

  public String getUserIdentifier() {
    return userIdentifier;
  }

  public TransferApiDto setUserIdentifier(String userIdentifier) {
    this.userIdentifier = userIdentifier;
    return this;
  }

  public String getOriginCurrency() {
    return originCurrency;
  }

  public TransferApiDto setOriginCurrency(String originCurrency) {
    this.originCurrency = originCurrency;
    return this;
  }

  public BigDecimal getAmountTransferred() {
    return amountTransferred;
  }

  public TransferApiDto setAmountTransferred(BigDecimal amountTransferred) {
    this.amountTransferred = amountTransferred;
    return this;
  }

  public BigDecimal getConversionFee() {
    return conversionFee;
  }

  public TransferApiDto setConversionFee(BigDecimal conversionFee) {
    this.conversionFee = conversionFee;
    return this;
  }

  public String getId() {
    return id;
  }

  public TransferApiDto setId(String id) {
    this.id = id;
    return this;
  }

  public String getOriginEntityIdentifier() {
    return originEntityIdentifier;
  }

  public TransferApiDto setOriginEntityIdentifier(String originEntityIdentifier) {
    this.originEntityIdentifier = originEntityIdentifier;
    return this;
  }

  public EntityType getOriginEntityType() {
    return originEntityType;
  }

  public TransferApiDto setOriginEntityType(EntityType originEntityType) {
    this.originEntityType = originEntityType;
    return this;
  }

  public String getTargetCurrency() {
    return targetCurrency;
  }

  public TransferApiDto setTargetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
    return this;
  }

  public String getTargetEntityIdentifier() {
    return targetEntityIdentifier;
  }

  public TransferApiDto setTargetEntityIdentifier(String targetEntityIdentifier) {
    this.targetEntityIdentifier = targetEntityIdentifier;
    return this;
  }

  public EntityType getTargetEntityType() {
    return targetEntityType;
  }

  public TransferApiDto setTargetEntityType(EntityType targetEntityType) {
    this.targetEntityType = targetEntityType;
    return this;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public TransferApiDto setTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }
}
