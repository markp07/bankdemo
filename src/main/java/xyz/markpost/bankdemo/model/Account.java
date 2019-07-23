package xyz.markpost.bankdemo.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @GeneratedValue(strategy = GenerationType.AUTO)
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
