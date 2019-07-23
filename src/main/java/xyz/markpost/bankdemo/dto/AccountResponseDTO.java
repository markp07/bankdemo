package xyz.markpost.bankdemo.dto;

import lombok.Getter;
import lombok.Setter;
import xyz.markpost.bankdemo.model.Client;

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
