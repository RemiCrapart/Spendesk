package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.api.CreateTransferApiDto;
import com.spendesk.WalletCards.model.api.TransferApiDto;
import com.spendesk.WalletCards.model.entity.Transfer;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

public class TransferMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  public static Transfer toEntity(
      CreateTransferApiDto createTransferApiDto, BusinessContext businessContext) {
    Transfer transfer = modelMapper.map(createTransferApiDto, Transfer.class);
    transfer.setUserIdentifier(businessContext.getUserId());
    transfer.setTimestamp(LocalDate.now().toString());

    return transfer;
  }

  public static TransferApiDto toApi(Transfer transfer) {
    return modelMapper.map(transfer, TransferApiDto.class);
  }
}
