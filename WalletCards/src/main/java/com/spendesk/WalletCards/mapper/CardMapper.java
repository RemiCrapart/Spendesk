package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.model.api.CardApiDto;
import com.spendesk.WalletCards.model.entity.Card;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  public static CardApiDto toApi(Card card) {
    return modelMapper.map(card, CardApiDto.class);
  }

  public static Card toEntity(String walletId, CardApiDto cardApiDto) {
    Card card= modelMapper.map(cardApiDto, Card.class);
    card.setWalletIdentifier(walletId);
    return card;
  }

  public static List<CardApiDto> toApis(List<Card> cardList) {
    List<CardApiDto> cardApiDtoList = new ArrayList<>();
    for (Card card : cardList) {
      cardApiDtoList.add(toApi(card));
    }
    return cardApiDtoList;
  }
}
