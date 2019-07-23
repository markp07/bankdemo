package xyz.markpost.bankdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.markpost.bankdemo.model.Client;

/**
 * AccountResponseDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO extends AccountRequestDTO {

  private long id;

  private String name;

  private String number;

  public void setName(Client client) {
    this.name = client.getFullName();
  }
}
