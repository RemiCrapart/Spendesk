package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.Currency;
import com.spendesk.WalletCards.model.api.CreateWalletApiDto;
import com.spendesk.WalletCards.model.api.WalletApiDto;
import com.spendesk.WalletCards.model.entity.Wallet;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletMapperTest {

  public static final BigDecimal BALANCE = new BigDecimal(50.5);

  public static final BigDecimal BALANCE_2 = new BigDecimal(20);

  public static final String COMPANY_IDENTIFIER = "12345";

  public static final String COMPANY_IDENTIFIER_2 = "67891";

  public static final String CURRENCY = "EUR";

  public static final ObjectId ID = new ObjectId();

  public static final ObjectId ID_2 = new ObjectId();

  public static final String USER_IDENTIFIER = "123456";

  @Test
  void toApi_shouldSuceed() {
    Wallet wallet =
        new Wallet()
            .setId(ID)
            .setCompanyIdentifier(COMPANY_IDENTIFIER)
            .setBalance(BALANCE)
            .setCurrency(CURRENCY)
            .setMasterWallet(false);

    WalletApiDto walletApiDto = WalletMapper.toApi(wallet);

    assertThat(walletApiDto).isNotNull();
    assertThat(walletApiDto.getId()).isEqualTo(ID.toString());
    assertThat(walletApiDto.getCompanyIdentifier()).isEqualTo(COMPANY_IDENTIFIER);
    assertThat(walletApiDto.getBalance()).isEqualTo(BALANCE);
    assertThat(walletApiDto.getCurrency()).isEqualTo(CURRENCY);
    assertThat(walletApiDto.isMasterWallet()).isEqualTo(false);
  }

  @Test
  void toApis_shouldSuceed() {
    Wallet wallet =
        new Wallet()
            .setId(ID)
            .setCompanyIdentifier(COMPANY_IDENTIFIER)
            .setBalance(BALANCE)
            .setCurrency(CURRENCY)
            .setMasterWallet(false);

    Wallet wallet2 =
        new Wallet()
            .setId(ID_2)
            .setCompanyIdentifier(COMPANY_IDENTIFIER_2)
            .setBalance(BALANCE_2)
            .setCurrency(CURRENCY)
            .setMasterWallet(true);

    List<Wallet> walletList = new ArrayList<>();
    walletList.add(wallet);
    walletList.add(wallet2);

    List<WalletApiDto> walletApiDtoList = WalletMapper.toApis(walletList);

    assertThat(walletApiDtoList).isNotNull();
    WalletApiDto walletApiDto = walletApiDtoList.get(0);
    assertThat(walletApiDto.getId()).isEqualTo(ID.toString());
    assertThat(walletApiDto.getCompanyIdentifier()).isEqualTo(COMPANY_IDENTIFIER);
    assertThat(walletApiDto.getBalance()).isEqualTo(BALANCE);
    assertThat(walletApiDto.getCurrency()).isEqualTo(CURRENCY);
    assertThat(walletApiDto.isMasterWallet()).isEqualTo(false);
    walletApiDto = walletApiDtoList.get(1);
    assertThat(walletApiDto.getId()).isEqualTo(ID_2.toString());
    assertThat(walletApiDto.getCompanyIdentifier()).isEqualTo(COMPANY_IDENTIFIER_2);
    assertThat(walletApiDto.getBalance()).isEqualTo(BALANCE_2);
    assertThat(walletApiDto.getCurrency()).isEqualTo(CURRENCY);
    assertThat(walletApiDto.isMasterWallet()).isEqualTo(true);
  }

  @Test
  void toEntity_shouldSuceed() throws SpendeskException {

    CreateWalletApiDto createWalletApiDto =
        new CreateWalletApiDto().setBalance(BALANCE).setCurrency(Currency.EUR);

    Wallet walletEntity =
        WalletMapper.toEntity(
            createWalletApiDto, new BusinessContext(USER_IDENTIFIER, COMPANY_IDENTIFIER));

    assertThat(walletEntity).isNotNull();
    assertThat(walletEntity.getCompanyIdentifier()).isEqualTo(COMPANY_IDENTIFIER);
    assertThat(walletEntity.getBalance()).isEqualTo(BALANCE);
    assertThat(walletEntity.getCurrency()).isEqualTo(CURRENCY);
    assertThat(walletEntity.isMasterWallet()).isEqualTo(false);
  }
}
