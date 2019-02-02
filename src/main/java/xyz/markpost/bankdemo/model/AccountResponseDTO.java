package xyz.markpost.bankdemo.model;

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

  public void setName(Client client) {
    this.name = client.getFullName();
  }
}
