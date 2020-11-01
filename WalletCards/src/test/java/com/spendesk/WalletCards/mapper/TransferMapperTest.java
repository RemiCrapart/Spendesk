package com.spendesk.WalletCards.mapper;

import com.spendesk.WalletCards.model.BusinessContext;
import com.spendesk.WalletCards.model.EntityType;
import com.spendesk.WalletCards.model.api.CreateTransferApiDto;
import com.spendesk.WalletCards.model.entity.Transfer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferMapperTest {

  public static final BigDecimal AMOUNT_TRANSFERRED = new BigDecimal(50);

  public static final String ORIGIN_ENTITY_ID = "1";
  public static final EntityType ORIGIN_ENTITY_TYPE = EntityType.CARD;
  public static final String TARGET_ENTITY_ID = "3";
  public static final EntityType TARGET_ENTITY_TYPE = EntityType.WALLET;
  public static final String USER_IDENTIFIER = "123";

  @Test
  void toEntity_shouldSucceed() {

    CreateTransferApiDto createTransferApiDto =
        new CreateTransferApiDto()
            .setAmountTransferred(AMOUNT_TRANSFERRED)
            .setOriginEntityIdentifier(ORIGIN_ENTITY_ID)
            .setOriginEntityType(ORIGIN_ENTITY_TYPE)
            .setTargetEntityIdentifier(TARGET_ENTITY_ID)
            .setTargetEntityType(TARGET_ENTITY_TYPE);

    Transfer transfer =
        TransferMapper.toEntity(createTransferApiDto, new BusinessContext(USER_IDENTIFIER, "1234"));

    assertThat(transfer).isNotNull();
    assertThat(transfer.getAmountTransferred()).isEqualTo(AMOUNT_TRANSFERRED);
    assertThat(transfer.getOriginEntityIdentifier()).isEqualTo(ORIGIN_ENTITY_ID);
    assertThat(transfer.getOriginEntityType()).isEqualTo(ORIGIN_ENTITY_TYPE);
    assertThat(transfer.getTargetEntityIdentifier()).isEqualTo(TARGET_ENTITY_ID);
    assertThat(transfer.getTargetEntityType()).isEqualTo(TARGET_ENTITY_TYPE);
    assertThat(transfer.getUserIdentifier()).isEqualTo(USER_IDENTIFIER);
  }
}
