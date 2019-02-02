package xyz.markpost.bankdemo.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

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
