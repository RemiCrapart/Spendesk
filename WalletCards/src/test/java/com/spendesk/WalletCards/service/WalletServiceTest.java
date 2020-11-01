package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.entity.Wallet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletServiceTest {

  public static final BigDecimal BALANCE = new BigDecimal(50);

  public static final String COMPANY_IDENTIFIER = "12345";

  public static final String CURRENCY = "EUR";

  public static final ObjectId ID = new ObjectId();

  public static final BusinessContext businessContext = new BusinessContext("123", "123");

  @Autowired private WalletService walletService;

  @Test
  public void create_shouldSuceed() {

    Wallet walletResult = walletService.create(initWallet());

    assertThat(walletResult).isNotNull();
    assertThat(walletResult.getCompanyIdentifier()).isEqualTo(COMPANY_IDENTIFIER);
    assertThat(walletResult.getBalance()).isEqualTo(BALANCE);
    assertThat(walletResult.getCurrency()).isEqualTo(CURRENCY);
    assertThat(walletResult.isMasterWallet()).isEqualTo(false);
  }

  @Test()
  public void findById_shouldSucceed() throws SpendeskException {
    Wallet wallet = initWallet();
    walletService.create(wallet);
    Wallet walletResult = walletService.findById(wallet.getId().toString());
  }

  @Test()
  public void findById_shouldFailWithUnknownWallet() {
    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          walletService.findById("1234");
        });
  }

  @Test()
  public void updateBalance_shouldSucceedWithUnloadMoney() throws SpendeskException {
    walletService.create(initWallet());

    Wallet wallet =
        walletService.updateBalance(ID.toString(), new BigDecimal(-40), businessContext);

    assertThat(wallet.getBalance()).isEqualTo(new BigDecimal(10));
  }

  @Test()
  public void updateBalance_shouldSucceedWithLoadMoney() throws SpendeskException {
    walletService.create(initWallet());

    Wallet wallet = walletService.updateBalance(ID.toString(), new BigDecimal(40), businessContext);

    assertThat(wallet.getBalance()).isEqualTo(new BigDecimal(90));
  }

  @Test()
  public void updateBalance_shouldFailWithUnknownWallet() {
    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          walletService.updateBalance("1234", new BigDecimal(10), businessContext);
        });
  }

  @Test()
  public void updateBalance_shouldFailWithNotEnoughMoney() {
    walletService.create(initWallet());
    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          walletService.updateBalance(ID.toString(), new BigDecimal(-100), businessContext);
        });
  }

  private Wallet initWallet() {
    return new Wallet()
        .setId(ID)
        .setBalance(BALANCE)
        .setCurrency(CURRENCY)
        .setCompanyIdentifier(COMPANY_IDENTIFIER)
        .setMasterWallet(false);
  }
}
