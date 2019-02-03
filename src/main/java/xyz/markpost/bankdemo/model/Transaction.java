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
 * Transaction entity
 */
@Entity
public class Transaction {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "contra_account_id")
  private Account contraAccount;

  @Getter
  @Setter
  @Column(name = "type")
  private TransactionType type;

  @Getter
  @Setter
  @Column(name = "date")
  private Date date;

  @Getter
  @Setter
  @Column(name = "amount")
  private float amount;

  @Getter
  @Setter
  @Column(name = "description")
  private String description;

}
