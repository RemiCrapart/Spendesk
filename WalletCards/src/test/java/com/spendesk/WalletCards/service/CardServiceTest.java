package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.CardStatus;
import com.spendesk.WalletCards.model.entity.Card;
import com.spendesk.WalletCards.model.entity.Wallet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardServiceTest {

  public static final BigDecimal BALANCE = new BigDecimal(50);

  public static final String COMPANY_IDENTIFIER = "12345";

  public static final String CURRENCY = "EUR";

  public static final ObjectId ID = new ObjectId();

  public static final String USER_ID = "123456";

  public static final BusinessContext businessContext =
      new BusinessContext(USER_ID, COMPANY_IDENTIFIER);

  @Autowired private CardService cardService;

  @Autowired private WalletService walletService;

  @BeforeAll
  public void initWallet() {
    Wallet wallet =
        new Wallet()
            .setId(ID)
            .setBalance(BALANCE)
            .setCurrency(CURRENCY)
            .setCompanyIdentifier(COMPANY_IDENTIFIER)
            .setMasterWallet(false);
    walletService.create(wallet);
  }

  @Test
  public void create_shouldSucceed() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);
    assertThat(card).isNotNull();
  }

  @Test
  public void create_shouldFailWithUnknownWallet() {
    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          Card card = cardService.create("1234", businessContext);
        });
  }

  @Test
  public void create_shouldFailWithWrongCompany() {
    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          Card card = cardService.create(ID.toString(), new BusinessContext(USER_ID, "123"));
        });
  }

  @Test
  public void updateBalance_shouldSucceed() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);

    card = cardService.updateBalance(card.getId().toString(), new BigDecimal(20), businessContext);

    Wallet wallet = walletService.findById(card.getWalletIdentifier());
    assertThat(card.getBalance()).isEqualTo(new BigDecimal(20));
    assertThat(wallet.getBalance()).isEqualTo(new BigDecimal(30));
  }

  @Test
  public void updateBalance_shouldFailWithCardNotEnoughMoney() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          cardService.updateBalance(card.getId().toString(), new BigDecimal(-100), businessContext);
        });
  }

  @Test
  public void updateBalance_shouldFailWithWalletNotEnoughMoney() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          cardService.updateBalance(card.getId().toString(), new BigDecimal(100), businessContext);
        });
  }

  @Test
  public void updateStatus_shouldSucceedWithActive() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);

    card =
        cardService.updateStatus(
            card.getId().toString(),
            CardStatus.ACTIVE,
            new BusinessContext(USER_ID, COMPANY_IDENTIFIER));
    assertThat(card.getStatus()).isEqualTo(CardStatus.ACTIVE);
  }

  @Test
  public void updateStatus_shouldSucceedWithBlock() throws Exception {
    Card card = cardService.create(ID.toString(), businessContext);
    cardService.updateBalance(
        card.getId().toString(),
        new BigDecimal(10),
        new BusinessContext(USER_ID, COMPANY_IDENTIFIER));

    card = cardService.updateStatus(card.getId().toString(), CardStatus.BLOCK, businessContext);

    assertThat(card.getStatus()).isEqualTo(CardStatus.BLOCK);
    assertThat(card.getBalance()).isEqualTo(new BigDecimal(0));

    Wallet wallet = walletService.findById(card.getWalletIdentifier());
    assertThat(wallet.getBalance()).isEqualTo(new BigDecimal(50));
  }

  @Test
  public void updateStatus_shouldFailWithUnknownCard() {

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          cardService.updateStatus("12345", CardStatus.ACTIVE, businessContext);
        });
  }
}
