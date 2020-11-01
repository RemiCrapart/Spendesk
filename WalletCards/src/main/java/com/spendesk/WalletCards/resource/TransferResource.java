package com.spendesk.WalletCards.resource;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.mapper.TransferMapper;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.api.CreateTransferApiDto;
import com.spendesk.WalletCards.model.api.TransferApiDto;
import com.spendesk.WalletCards.service.CardService;
import com.spendesk.WalletCards.service.TransferService;
import com.spendesk.WalletCards.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferResource {

  private final CardService cardService;

  private final TransferService transferService;

  private final WalletService walletService;

  Logger logger = LoggerFactory.getLogger(TransferResource.class);

  @Autowired
  public TransferResource(
      CardService cardService, WalletService walletService, TransferService transferService) {
    this.cardService = cardService;
    this.walletService = walletService;
    this.transferService = transferService;
  }

  @PostMapping("/transfers")
  public TransferApiDto createTransfer(
      @RequestHeader(ResourceConstants.USER_HEADER) String userHeader,
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @RequestBody CreateTransferApiDto createTransferApiDto)
      throws SpendeskException {
    return TransferMapper.toApi(
        transferService.create(
            TransferMapper.toEntity(
                createTransferApiDto, new BusinessContext(userHeader, companyHeader)),
            new BusinessContext(userHeader, companyHeader)));
  }
}
