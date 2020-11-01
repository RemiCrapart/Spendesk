package com.spendesk.WalletCards;

import com.spendesk.WalletCards.model.entity.Wallet;
import com.spendesk.WalletCards.service.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.math.BigDecimal;

/*
 Limitation : you can do transfer only if the origin is in EUR because of api limitation

 Add userIdentifier on Transfer to keep it in db
 Add auditTable for updateBalance betweend card/wallet
*/
@SpringBootApplication
@EnableAutoConfiguration
public class WalletCardsApplication {

  public static void main(String[] args) {
    SpringApplication.run(WalletCardsApplication.class, args);
  }

  @Bean
  public MoneyModule addMoneyModule() {
    return new MoneyModule();
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CommandLineRunner setup(WalletService walletService) {
    return (args) -> {
      walletService.create(
          new Wallet()
              .setMasterWallet(true)
              .setBalance(new BigDecimal(0))
              .setCurrency("EUR")
              .setCompanyIdentifier("SPENDESK"));
      walletService.create(
          new Wallet()
              .setMasterWallet(true)
              .setBalance(new BigDecimal(0))
              .setCurrency("USD")
              .setCompanyIdentifier("SPENDESK"));

      walletService.create(
          new Wallet()
              .setMasterWallet(true)
              .setBalance(new BigDecimal(0))
              .setCurrency("GBP")
              .setCompanyIdentifier("SPENDESK"));
    };
  }
}
