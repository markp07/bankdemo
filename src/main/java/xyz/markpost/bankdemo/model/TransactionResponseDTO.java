package xyz.markpost.bankdemo.model;

import java.sql.Date;
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
 * TransactionResponseDTO
 */
public class TransactionResponseDTO extends TransactionRequestDTO {

  @Getter
  @Setter
  private long id;

  @Getter
  private String accountName;

  @Getter
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
