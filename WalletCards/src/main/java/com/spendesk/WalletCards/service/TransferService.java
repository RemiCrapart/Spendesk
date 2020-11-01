package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.Currency;
import com.spendesk.WalletCards.model.EntityType;
import com.spendesk.WalletCards.model.client.FixerRatesClientDto;
import com.spendesk.WalletCards.model.entity.Transfer;
import com.spendesk.WalletCards.model.entity.Wallet;
import com.spendesk.WalletCards.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class TransferService {

  // Could be put in a properties
  public static final BigDecimal FEE_RATE = new BigDecimal(2.9);

  Logger logger = LoggerFactory.getLogger(TransferService.class);

  TransferRepository transferRepository;

  WalletService walletService;

  RestTemplate restTemplate;

  @Autowired
  public TransferService(
      TransferRepository transferRepository,
      RestTemplate restTemplate,
      WalletService walletService) {
    this.transferRepository = transferRepository;
    this.restTemplate = restTemplate;
    this.walletService = walletService;
  }

  public Transfer create(Transfer transfer, BusinessContext businessContext)
      throws SpendeskException {

    // check that origin and target are wallet (scope limitation)
    checkOriginAndTargetType(transfer);

    // find origin and target wallet and set the origin currency for the transfer
    Wallet originWallet = walletService.findById(transfer.getOriginEntityIdentifier());
    Wallet targetWallet = walletService.findById(transfer.getTargetEntityIdentifier());
    transfer.setOriginCurrency(originWallet.getCurrency());
    transfer.setTargetCurrency(targetWallet.getCurrency());

    // Free version of fixer.io doesn't manage other base than EUR :(
    if (!originWallet.getCurrency().equals(Currency.EUR.name())) {
      throw new SpendeskException(
          "Not possible to transfer if the origin is not in EUR",
          "TRANBUS02",
          HttpStatus.BAD_REQUEST);
    }

    // check security access on origin wallet
    walletService.checkAccess(originWallet, businessContext);

    // If origin and target currency not the same then convert
    BigDecimal amountToTransferConverted = transfer.getAmountTransferred();
    if (transfer.getOriginCurrency() != transfer.getTargetCurrency()) {
      amountToTransferConverted = convertAmount(transfer);
    }

    // Calculate fees
    BigDecimal fee = amountToTransferConverted.multiply(FEE_RATE).divide(new BigDecimal(100));
    BigDecimal amountToTransferWithFeeDeducted = amountToTransferConverted.add(fee.negate());

    // transfer money between wallet
    walletService.updateBalance(
        originWallet, transfer.getAmountTransferred().negate(), businessContext);
    walletService.updateBalance(targetWallet, amountToTransferWithFeeDeducted, businessContext);

    // update spendesk wallet
    walletService.updateBalance(
        walletService.findById(WalletService.spendeskWalletIds.get(transfer.getTargetCurrency())),
        fee,
        businessContext);

    logger.info(
        "User id [{}] of Company [{}] transfer [{}] {} from [{}] to [{}]",
        businessContext.getUserId(),
        businessContext.getCompanyId(),
        transfer.getAmountTransferred(),
        transfer.getOriginCurrency(),
        transfer.getOriginEntityIdentifier(),
        transfer.getTargetEntityIdentifier());

    // save the transfer
    return transferRepository.save(transfer);
  }

  /*
  Convert amount from EUR to anything else by calling fixer.io API (free version so no other base than EUR)
   */
  private BigDecimal convertAmount(Transfer transfer) {

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<String>(headers);

    FixerRatesClientDto fixerRatesClientDto =
        restTemplate
            .exchange(
                "http://data.fixer.io/api/latest?access_key=0c9acc9eb13cef9f3d4b72a6f6df18e9&symbols=EUR,USD,GBP",
                HttpMethod.GET,
                entity,
                FixerRatesClientDto.class)
            .getBody();

    BigDecimal initialAmount = transfer.getAmountTransferred();
    BigDecimal conversionFee = fixerRatesClientDto.getRates().get(transfer.getTargetCurrency());
    transfer.setConversionFee(conversionFee);
    return initialAmount.multiply(conversionFee);
  }

  /*
  Scope limitation only wallet to wallet transfer are allowed
   */
  private void checkOriginAndTargetType(Transfer transfer) throws SpendeskException {
    if (transfer.getOriginEntityType() != EntityType.WALLET
        || transfer.getTargetEntityType() != EntityType.WALLET) {
      throw new SpendeskException(
          "Transfer from  " + transfer.getOriginEntityType() + " is not allowed",
          "TRANBUS01",
          HttpStatus.BAD_REQUEST);
    }
  }
}
