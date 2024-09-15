package xyz.markpost.bankdemo.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.markpost.bankdemo.model.TransactionType;

/**
 * TransactionRequestDTO
 * <p>
 * TODO: add account number? Maybe instead of id?
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

  private long accountId;

  private long contraAccountId;

  private TransactionType type;

  private Date date;

  private float amount;

  private String description;

}
