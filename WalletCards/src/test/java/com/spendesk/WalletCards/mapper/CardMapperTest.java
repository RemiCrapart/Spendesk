package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.model.CardStatus;
import com.spendesk.WalletCards.model.api.CardApiDto;
import com.spendesk.WalletCards.model.entity.Card;
import org.bson.types.ObjectId;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CardMapperTest {
  public static final BigDecimal BALANCE = new BigDecimal(50.5);

  public static final String CARD_PIN_NUMBER = "1111111111111111";

  public static final String CCV = "123";

  public static final String CURRENCY = "EUR";

  public static final LocalDate EXPIRATION_DATE = LocalDate.of(2020, 10, 31);

  public static final ObjectId ID = new ObjectId();

  public static final ObjectId ID_2 = new ObjectId();

  public static final String USER_IDENTIFIER = "userId";

  public static final String WALLET_IDENTIFIER = "123";

  @Test
  void toApi_shouldSuceed() {
    Card card =
        new Card()
            .setCardPinNumber(CARD_PIN_NUMBER)
            .setBalance(BALANCE)
            .setCCV(CCV)
            .setExpirationDate(EXPIRATION_DATE)
            .setUserIdentifier(USER_IDENTIFIER)
            .setWalletIdentifier(WALLET_IDENTIFIER)
            .setStatus(CardStatus.ACTIVE)
            .setId(ID);

    CardApiDto cardApiDto = CardMapper.toApi(card);

    assertThat(cardApiDto).isNotNull();
    assertThat(cardApiDto.getBalance()).isEqualTo(BALANCE);
    assertThat(cardApiDto.getCardPinNumber()).isEqualTo(CARD_PIN_NUMBER);
    assertThat(cardApiDto.getCCV()).isEqualTo(CCV);
    assertThat(cardApiDto.getExpirationDate()).isEqualTo(EXPIRATION_DATE);
    assertThat(cardApiDto.getWalletIdentifier()).isEqualTo(WALLET_IDENTIFIER);
    assertThat(cardApiDto.getUserIdentifier()).isEqualTo(USER_IDENTIFIER);
  }

  @Test
  void toApis_shouldSuceed() {
    Card card =
        new Card()
            .setCardPinNumber(CARD_PIN_NUMBER)
            .setBalance(BALANCE)
            .setCCV(CCV)
            .setExpirationDate(EXPIRATION_DATE)
            .setUserIdentifier(USER_IDENTIFIER)
            .setWalletIdentifier(WALLET_IDENTIFIER)
            .setStatus(CardStatus.ACTIVE)
            .setId(ID);

    Card card2 =
        new Card()
            .setCardPinNumber(CARD_PIN_NUMBER)
            .setBalance(BALANCE)
            .setCCV(CCV)
            .setExpirationDate(EXPIRATION_DATE)
            .setUserIdentifier(USER_IDENTIFIER)
            .setWalletIdentifier(WALLET_IDENTIFIER)
            .setStatus(CardStatus.ACTIVE)
            .setId(ID_2);

    List<Card> cardList = new ArrayList<>();
    cardList.add(card);
    cardList.add(card2);

    List<CardApiDto> cardApiDtoList = CardMapper.toApis(cardList);
    assertThat(cardApiDtoList).isNotNull();
    CardApiDto cardApiDto = cardApiDtoList.get(0);

    assertThat(cardApiDto).isNotNull();
    assertThat(cardApiDto.getId()).isEqualTo(ID.toString());
    assertThat(cardApiDto.getBalance()).isEqualTo(BALANCE);
    assertThat(cardApiDto.getCardPinNumber()).isEqualTo(CARD_PIN_NUMBER);
    assertThat(cardApiDto.getCCV()).isEqualTo(CCV);
    assertThat(cardApiDto.getExpirationDate()).isEqualTo(EXPIRATION_DATE);
    assertThat(cardApiDto.getWalletIdentifier()).isEqualTo(WALLET_IDENTIFIER);
    assertThat(cardApiDto.getUserIdentifier()).isEqualTo(USER_IDENTIFIER);

    cardApiDto = cardApiDtoList.get(1);
    assertThat(cardApiDto).isNotNull();
    assertThat(cardApiDto.getId()).isEqualTo(ID_2.toString());
    assertThat(cardApiDto.getBalance()).isEqualTo(BALANCE);
    assertThat(cardApiDto.getCardPinNumber()).isEqualTo(CARD_PIN_NUMBER);
    assertThat(cardApiDto.getCCV()).isEqualTo(CCV);
    assertThat(cardApiDto.getExpirationDate()).isEqualTo(EXPIRATION_DATE);
    assertThat(cardApiDto.getWalletIdentifier()).isEqualTo(WALLET_IDENTIFIER);
    assertThat(cardApiDto.getUserIdentifier()).isEqualTo(USER_IDENTIFIER);
  }

  @Test
  void toEntity_shouldSuceed() {
    CardApiDto cardApiDto =
        new CardApiDto()
            .setCardPinNumber(CARD_PIN_NUMBER)
            .setBalance(BALANCE)
            .setCCV(CCV)
            .setExpirationDate(EXPIRATION_DATE)
            .setUserIdentifier(USER_IDENTIFIER)
            .setStatus(CardStatus.ACTIVE);

    Card card = CardMapper.toEntity(WALLET_IDENTIFIER, cardApiDto);

    assertThat(card).isNotNull();
    assertThat(card.getBalance()).isEqualTo(BALANCE);
    assertThat(card.getCardPinNumber()).isEqualTo(CARD_PIN_NUMBER);
    assertThat(card.getCCV()).isEqualTo(CCV);
    assertThat(card.getExpirationDate()).isEqualTo(EXPIRATION_DATE);
    assertThat(card.getWalletIdentifier()).isEqualTo(WALLET_IDENTIFIER);
    assertThat(card.getUserIdentifier()).isEqualTo(USER_IDENTIFIER);
  }
}
