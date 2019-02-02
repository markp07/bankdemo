package xyz.markpost.bankdemo.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * AccountResponseDTO
 */
public class AccountResponseDTO extends AccountRequestDTO {

  @Getter
  @Setter
  private long id;

  @Getter
  private String name;

  @Getter
  @Setter
  private String number;

  public void setName(String firstName, String lastName) {
    this.name = firstName + " " + lastName;
  }
}
