package xyz.markpost.bankdemo.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Account entity TODO: add not null TODO: add validation
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account-sequence-generator")
  @GenericGenerator(
      name = "account-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "account_sequence"),
          @Parameter(name = "initial_value", value = "5"),
          @Parameter(name = "increment_size", value = "1")
      }
  )
  @Column(name = "id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  //TODO: set validation min & max length and chars + numbers combination
  @Column(name = "number")
  private String number;

  @Column(name = "type")
  private AccountType type;

  @Column(name = "balance")
  private float balance;

  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @OneToMany(mappedBy = "contraAccount")
  private List<Transaction> contraTransactions;

}
