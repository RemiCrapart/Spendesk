package com.spendesk.WalletCards.model;

public class BusinessContext {

  private String companyId;

  private String userId;

  private BusinessContext() {}

  public BusinessContext(String userId, String companyId) {
    this.userId = userId;
    this.companyId = companyId;
  }

  public String getUserId() {
    return userId;
  }

  public BusinessContext setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public String getCompanyId() {
    return companyId;
  }

  public BusinessContext setCompanyId(String companyId) {
    this.companyId = companyId;
    return this;
  }
}
