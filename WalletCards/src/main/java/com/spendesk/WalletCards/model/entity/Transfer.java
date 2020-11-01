package com.spendesk.WalletCards.model.entity;

import com.spendesk.WalletCards.model.EntityType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Transfer {

  private BigDecimal amountTransferred;

  private BigDecimal conversionFee;

  @Id private ObjectId id;

  private String originCurrency;

  private String originEntityIdentifier;

  private EntityType originEntityType;

  private String targetCurrency;

  private String targetEntityIdentifier;

  private EntityType targetEntityType;

  private String timestamp;

  private String userIdentifier;

  public String getUserIdentifier() {
    return userIdentifier;
  }

  public Transfer setUserIdentifier(String userIdentifier) {
    this.userIdentifier = userIdentifier;
    return this;
  }

  public BigDecimal getAmountTransferred() {
    return amountTransferred;
  }

  public Transfer setAmountTransferred(BigDecimal amountTransferred) {
    this.amountTransferred = amountTransferred;
    return this;
  }

  public String getOriginCurrency() {
    return originCurrency;
  }

  public Transfer setOriginCurrency(String originCurrency) {
    this.originCurrency = originCurrency;
    return this;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Transfer setTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public ObjectId getId() {
    return id;
  }

  public Transfer setId(ObjectId id) {
    this.id = id;
    return this;
  }

  public String getTargetCurrency() {
    return targetCurrency;
  }

  public Transfer setTargetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
    return this;
  }

  public BigDecimal getConversionFee() {
    return conversionFee;
  }

  public Transfer setConversionFee(BigDecimal conversionFee) {
    this.conversionFee = conversionFee;
    return this;
  }

  public String getOriginEntityIdentifier() {
    return originEntityIdentifier;
  }

  public Transfer setOriginEntityIdentifier(String originEntityIdentifier) {
    this.originEntityIdentifier = originEntityIdentifier;
    return this;
  }

  public EntityType getOriginEntityType() {
    return originEntityType;
  }

  public Transfer setOriginEntityType(EntityType originEntityType) {
    this.originEntityType = originEntityType;
    return this;
  }

  public String getTargetEntityIdentifier() {
    return targetEntityIdentifier;
  }

  public Transfer setTargetEntityIdentifier(String targetEntityIdentifier) {
    this.targetEntityIdentifier = targetEntityIdentifier;
    return this;
  }

  public EntityType getTargetEntityType() {
    return targetEntityType;
  }

  public Transfer setTargetEntityType(EntityType targetEntityType) {
    this.targetEntityType = targetEntityType;
    return this;
  }
}
