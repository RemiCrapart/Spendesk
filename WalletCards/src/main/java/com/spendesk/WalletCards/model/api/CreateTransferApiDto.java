package com.spendesk.WalletCards.model.api;

import com.spendesk.WalletCards.model.EntityType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateTransferApiDto {
  @NotNull private BigDecimal amountTransferred;

  private String conversionFee;

  @NotNull private String originEntityIdentifier;

  @NotNull private EntityType originEntityType;

  @NotNull private String targetEntityIdentifier;

  @NotNull private EntityType targetEntityType;

  public CreateTransferApiDto() {}

  public EntityType getOriginEntityType() {
    return originEntityType;
  }

  public CreateTransferApiDto setOriginEntityType(EntityType originEntityType) {
    this.originEntityType = originEntityType;
    return this;
  }

  public EntityType getTargetEntityType() {
    return targetEntityType;
  }

  public CreateTransferApiDto setTargetEntityType(EntityType targetEntityType) {
    this.targetEntityType = targetEntityType;
    return this;
  }

  public BigDecimal getAmountTransferred() {
    return amountTransferred;
  }

  public CreateTransferApiDto setAmountTransferred(BigDecimal amountTransferred) {
    this.amountTransferred = amountTransferred;
    return this;
  }

  public String getConversionFee() {
    return conversionFee;
  }

  public CreateTransferApiDto setConversionFee(String conversionFee) {
    this.conversionFee = conversionFee;
    return this;
  }

  public String getOriginEntityIdentifier() {
    return originEntityIdentifier;
  }

  public CreateTransferApiDto setOriginEntityIdentifier(String originEntityIdentifier) {
    this.originEntityIdentifier = originEntityIdentifier;
    return this;
  }

  public String getTargetEntityIdentifier() {
    return targetEntityIdentifier;
  }

  public CreateTransferApiDto setTargetEntityIdentifier(String targetEntityIdentifier) {
    this.targetEntityIdentifier = targetEntityIdentifier;
    return this;
  }
}
