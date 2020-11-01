package com.spendesk.WalletCards.service;

import com.spendesk.WalletCards.error.SpendeskException;
import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.entity.Wallet;
import com.spendesk.WalletCards.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class WalletService {

  public static Map<String, String> spendeskWalletIds = new HashMap<>();

  Logger logger = LoggerFactory.getLogger(WalletService.class);

  private WalletRepository walletRepository;

  @Autowired
  public WalletService(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  public Wallet create(Wallet wallet) {
    wallet = walletRepository.save(wallet);
    if (wallet.isMasterWallet()) {
      spendeskWalletIds.put(wallet.getCurrency(), wallet.getId().toString());
    }

    return wallet;
  }

  public List<Wallet> findAll(BusinessContext businessContext) {
    return walletRepository.findByCompanyIdentifier(businessContext.getCompanyId());
  }

  public Wallet findById(String walletId) throws SpendeskException {
    Optional wallet = walletRepository.findById(walletId);
    if (!wallet.isPresent()) {
      throw new SpendeskException(
          "This wallet id [" + walletId + "]doesn't not exit", "WALLBUS01", HttpStatus.NOT_FOUND);
    }
    return walletRepository.findById(walletId).get();
  }

  /*
  Check if logged company user is the same than the wallet we are using
   */
  public void checkAccess(Wallet wallet, BusinessContext businessContext) throws SpendeskException {
    if (!wallet.getCompanyIdentifier().equals(businessContext.getCompanyId())) {
      throw new SpendeskException(
          "This wallet doesn't belong to this company", "WALLBUS02", HttpStatus.NOT_FOUND);
    }
  }

  public Wallet update(Wallet wallet) {
    return walletRepository.save(wallet);
  }

  /*
   Add the specific amount (could be negative) to the balance of the specified wallet
  */
  public Wallet updateBalance(Wallet wallet, BigDecimal amount, BusinessContext businessContext)
      throws SpendeskException {

    BigDecimal currentBalance = wallet.getBalance();
    BigDecimal newBalance = currentBalance.add(amount);

    // Check wallet balance will not be negative
    if (newBalance.signum() < 0) {
      throw new SpendeskException(
          "There is not enough money on the wallet", "WALLBUS03", HttpStatus.BAD_REQUEST);
    }
    wallet.setBalance(newBalance);

    logger.info(
        "User id [{}] of Company [{}] update balance wallet [{}] with [{}] {} as amount",
        businessContext.getUserId(),
        businessContext.getCompanyId(),
        wallet.getId().toString(),
        amount,
        wallet.getCurrency());

    return this.update(wallet);
  }

  public Wallet updateBalance(String walletId, BigDecimal amount, BusinessContext businessContext)
      throws SpendeskException {
    Wallet wallet = this.findById(walletId);
    return updateBalance(wallet, amount, businessContext);
  }
}
