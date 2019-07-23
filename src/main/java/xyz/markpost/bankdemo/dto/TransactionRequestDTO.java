package xyz.markpost.bankdemo.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import xyz.markpost.bankdemo.model.TransactionType;

/**
 * TransactionRequestDTO
 *
 * TODO: add account number? Maybe instead of id?
 */
public class TransactionRequestDTO {

  @Getter
  @Setter
  private long accountId;

  @Getter
  @Setter
  private long contraAccountId;

  @Getter
  @Setter
  private TransactionType type;

  @Getter
  @Setter
  private Date date;

  @Getter
  @Setter
  private float amount;

  @Getter
  @Setter
  private String description;

}
