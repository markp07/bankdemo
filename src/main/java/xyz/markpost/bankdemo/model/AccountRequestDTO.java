package xyz.markpost.bankdemo.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

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
