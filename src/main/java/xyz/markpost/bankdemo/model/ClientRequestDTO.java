package xyz.markpost.bankdemo.model;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class ClientRequestDTO {

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Getter
  @Setter
  private Date birthDate;

  @Getter
  @Setter
  private String address;

}
