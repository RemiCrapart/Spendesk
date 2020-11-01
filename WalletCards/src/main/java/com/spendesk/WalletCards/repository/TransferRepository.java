package com.spendesk.WalletCards.repository;

import com.spendesk.WalletCards.model.entity.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransferRepository extends MongoRepository<Transfer, String> {}
