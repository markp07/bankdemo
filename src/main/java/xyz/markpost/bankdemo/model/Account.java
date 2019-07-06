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
import lombok.Getter;
import lombok.Setter;

/**
 * Account entity TODO: add not null TODO: add validation
 */
@Entity
public class Account {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  //TODO: set validation min & max length and chars + numbers combination
  @Getter
  @Setter
  @Column(name = "number")
  private String number;

  @Getter
  @Setter
  @Column(name = "type")
  private AccountType type;

  @Getter
  @Setter
  @Column(name = "balance")
  private float balance;

  @Getter
  @Setter
  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @Getter
  @Setter
  @OneToMany(mappedBy = "contraAccount")
  private List<Transaction> contraTransactions;

}
