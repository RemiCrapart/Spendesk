package com.spendesk.WalletCards.repository;

import com.spendesk.WalletCards.model.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {

  List<Card> findByWalletIdentifier(String walletIdentifier);
}
