package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.api.CreateWalletApiDto;
import com.spendesk.WalletCards.model.api.WalletApiDto;
import com.spendesk.WalletCards.model.entity.Wallet;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class WalletMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  public static WalletApiDto toApi(Wallet wallet) {
    return modelMapper.map(wallet, WalletApiDto.class);
  }

  public static Wallet toEntity(
      CreateWalletApiDto createWalletApiDto, BusinessContext businessContext) {

    Wallet wallet = modelMapper.map(createWalletApiDto, Wallet.class);
    wallet.setMasterWallet(false).setCompanyIdentifier(businessContext.getCompanyId());
    return wallet;
  }

  public static List<WalletApiDto> toApis(List<Wallet> walletList) {
    List<WalletApiDto> walletApiDtoList = new ArrayList<>();
    for (Wallet wallet : walletList) {
      walletApiDtoList.add(toApi(wallet));
    }
    return walletApiDtoList;
  }
}
