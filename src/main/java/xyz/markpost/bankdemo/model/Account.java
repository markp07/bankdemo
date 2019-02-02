package xyz.markpost.bankdemo.model;

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
 * Account entity
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

}
