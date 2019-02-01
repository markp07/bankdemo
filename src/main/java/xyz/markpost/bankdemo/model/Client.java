package xyz.markpost.bankdemo.model;

import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Client entity
 */
@Entity
public class Client {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Getter
  @Setter
  @Column(name = "firstname")
  private String firstName;

  @Getter
  @Setter
  @Column(name = "lastname")
  private String lastName;

  @Getter
  @Setter
  @Column(name = "birthdate")
  private Date birthDate;

  @Getter
  @Setter
  @Column(name = "address")
  private String address;

  @Getter
  @Setter
  @OneToMany(mappedBy = "client")
  private List<Account> clients;

  @Getter
  @Setter
  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @Getter
  @Setter
  @OneToMany(mappedBy = "contraAccount")
  private List<Transaction> contraTransactions;

}
