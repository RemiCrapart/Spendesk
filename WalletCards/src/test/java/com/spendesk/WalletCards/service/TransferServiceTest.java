package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.EntityType;
import com.spendesk.WalletCards.model.entity.Transfer;
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
public class TransferServiceTest {

  public static final BigDecimal BALANCE = new BigDecimal(50);

  public static final String COMPANY_IDENTIFIER = "12345";

  public static final String CURRENCY = "EUR";

  public static final String CURRENCY_2 = "USD";

  public static final ObjectId ID = new ObjectId();

  public static final ObjectId ID_2 = new ObjectId();

  public static final String USER_ID = "123456";

  public static final BusinessContext businessContext =
      new BusinessContext("123", COMPANY_IDENTIFIER);

  @Autowired private TransferService transferService;

  @Autowired private WalletService walletService;

  @BeforeAll
  public void initWallets() {
    Wallet wallet =
        new Wallet()
            .setId(ID)
            .setBalance(BALANCE)
            .setCurrency(CURRENCY)
            .setCompanyIdentifier(COMPANY_IDENTIFIER)
            .setMasterWallet(false);
    walletService.create(wallet);

    wallet =
        new Wallet()
            .setId(ID_2)
            .setBalance(BALANCE)
            .setCurrency(CURRENCY_2)
            .setCompanyIdentifier(COMPANY_IDENTIFIER)
            .setMasterWallet(false);
    walletService.create(wallet);
  }

  @Test
  public void create_shouldSucceed() throws Exception {

    Wallet spendeskWallet = walletService.findById(WalletService.spendeskWalletIds.get(CURRENCY));
    BigDecimal previousBalanceSpendesk = spendeskWallet.getBalance();

    BigDecimal previousBalanceOrigin = walletService.findById(ID.toString()).getBalance();
    BigDecimal previousBalanceTarget = walletService.findById(ID_2.toString()).getBalance();

    Transfer transfer =
        new Transfer()
            .setAmountTransferred(new BigDecimal(10))
            .setOriginEntityIdentifier(ID.toString())
            .setTargetEntityIdentifier(ID_2.toString())
            .setOriginEntityType(EntityType.WALLET)
            .setTargetEntityType(EntityType.WALLET);

    transfer = transferService.create(transfer, businessContext);

    assertThat(transfer).isNotNull();
    assertThat(previousBalanceSpendesk)
        .isLessThan(
            walletService.findById(WalletService.spendeskWalletIds.get(CURRENCY_2)).getBalance());
    assertThat(previousBalanceOrigin.add(new BigDecimal(-10)))
        .isEqualTo(walletService.findById(ID.toString()).getBalance());
    assertThat(previousBalanceTarget)
        .isLessThan(walletService.findById(ID_2.toString()).getBalance());
  }

  @Test
  public void create_shouldFailNotEnoughMoney() throws Exception {

    Transfer transfer =
        new Transfer()
            .setAmountTransferred(new BigDecimal(60000))
            .setOriginEntityIdentifier(ID.toString())
            .setTargetEntityIdentifier(ID_2.toString())
            .setOriginEntityType(EntityType.WALLET)
            .setTargetEntityType(EntityType.WALLET);

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          transferService.create(transfer, businessContext);
        });
  }

  @Test
  public void create_shouldFailOriginNotEURCurrency() throws Exception {

    Transfer transfer =
        new Transfer()
            .setAmountTransferred(new BigDecimal(10))
            .setOriginEntityIdentifier(ID_2.toString())
            .setTargetEntityIdentifier(ID.toString())
            .setOriginEntityType(EntityType.WALLET)
            .setTargetEntityType(EntityType.WALLET);

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          transferService.create(transfer, businessContext);
        });
  }

  @Test
  public void create_shouldFailOriginNotaWallet() throws Exception {

    Transfer transfer =
        new Transfer()
            .setAmountTransferred(new BigDecimal(10))
            .setOriginEntityIdentifier(ID.toString())
            .setTargetEntityIdentifier(ID_2.toString())
            .setOriginEntityType(EntityType.CARD)
            .setTargetEntityType(EntityType.WALLET);

    Assertions.assertThrows(
        SpendeskException.class,
        () -> {
          transferService.create(transfer, businessContext);
        });
  }
}
