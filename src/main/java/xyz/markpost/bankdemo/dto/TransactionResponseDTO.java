package xyz.markpost.bankdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.Client;

/**
 * The DTO for transaction data. We don't want to send all data of the entity back to the requester
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO extends TransactionRequestDTO {

  private long id;

  private String accountName;

  private String contraAccountName;

  public void setAccountName(Account account) {
    Client client = account.getClient();
    this.accountName = client.getFullName();
  }

  public void setContraAccountName(Account contraAccount) {
    Client client = contraAccount.getClient();
    this.contraAccountName = client.getFullName();
  }
}
