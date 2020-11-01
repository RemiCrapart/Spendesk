package com.spendesk.WalletCards.repository;

import com.spendesk.WalletCards.model.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WalletRepository extends MongoRepository<Wallet, String> {

  List<Wallet> findByCompanyIdentifier(String companyIdentifier);
}
