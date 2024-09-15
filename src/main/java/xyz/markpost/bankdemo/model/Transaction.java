package xyz.markpost.bankdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Transaction entity TODO: add not null TODO: add validation
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction-sequence-generator")
  @GenericGenerator(
      name = "transaction-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "transaction_sequence"),
          @Parameter(name = "initial_value", value = "5"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Column(name = "id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "contra_account_id")
  private Account contraAccount;

  @Column(name = "type")
  private TransactionType type;

  @Column(name = "date")
  private Date date;

  @Column(name = "amount")
  private float amount;

  @Column(name = "description")
  private String description;

}
