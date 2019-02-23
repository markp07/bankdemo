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
 * Client entity TODO: add not null TODO: add validation
 */
@Entity
public class Client {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id")
  private long id;

  @Getter
  @Setter
  @Column(name = "first_name")
  private String firstName;

  @Getter
  @Setter
  @Column(name = "last_name")
  private String lastName;

  @Getter
  @Setter
  @Column(name = "birth_date")
  private Date birthDate;

  @Getter
  @Setter
  @Column(name = "address")
  private String address;

  @Getter
  @Setter
  @OneToMany(mappedBy = "client")
  private List<Account> accounts;

  public String getFullName() {
    return firstName + " " + lastName;
  }

}
