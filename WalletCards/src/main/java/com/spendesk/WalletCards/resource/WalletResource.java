package com.spendesk.WalletCards.resource;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.mapper.CardMapper;
import com.spendesk.WalletCards.mapper.WalletMapper;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.api.CardApiDto;
import com.spendesk.WalletCards.model.api.CreateWalletApiDto;
import com.spendesk.WalletCards.model.api.WalletApiDto;
import com.spendesk.WalletCards.service.CardService;
import com.spendesk.WalletCards.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WalletResource {

  private final CardService cardService;

  private final WalletService walletService;

  Logger logger = LoggerFactory.getLogger(WalletResource.class);

  @Autowired
  public WalletResource(WalletService walletService, CardService cardService) {
    this.walletService = walletService;
    this.cardService = cardService;
  }

  @PostMapping("/wallets/{walletId}/cards")
  public CardApiDto createCard(
      @RequestHeader(ResourceConstants.USER_HEADER) String userHeader,
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @PathVariable String walletId)
      throws Exception {
    return CardMapper.toApi(
        cardService.create(walletId, new BusinessContext(userHeader, companyHeader)));
  }

  @Operation(summary = "Create wallet")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Wallet created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = WalletApiDto.class))
            })
      })
  @PostMapping("/wallets")
  public WalletApiDto createWallet(
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @Valid @RequestBody CreateWalletApiDto createwalletApiDto)
      throws SpendeskException {
    return WalletMapper.toApi(
        walletService.create(
            WalletMapper.toEntity(createwalletApiDto, new BusinessContext("", companyHeader))));
  }

  @GetMapping("/wallets/{walletId}/cards")
  public List<CardApiDto> findAllCards(
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader,
      @PathVariable String walletId)
      throws SpendeskException {
    return CardMapper.toApis(
        cardService.findByWalletIdentifier(walletId, new BusinessContext("", companyHeader)));
  }

  @GetMapping("/wallets")
  public List<WalletApiDto> findAllWallets(
      @RequestHeader(ResourceConstants.COMPANY_HEADER) String companyHeader) {
    return WalletMapper.toApis(walletService.findAll(new BusinessContext("", companyHeader)));
  }
}
