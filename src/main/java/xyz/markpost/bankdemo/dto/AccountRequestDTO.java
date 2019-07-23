package xyz.markpost.bankdemo.dto;

import lombok.Getter;
import lombok.Setter;
import xyz.markpost.bankdemo.model.AccountType;

/**
 * AccountRequestDTO
 */
public class AccountRequestDTO {

  @Getter
  @Setter
  private Long clientId;

  @Getter
  @Setter
  private AccountType type;

}
