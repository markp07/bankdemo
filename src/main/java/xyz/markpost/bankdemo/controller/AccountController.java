package xyz.markpost.bankdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.bankdemo.model.AccountRequestDTO;
import xyz.markpost.bankdemo.model.AccountResponseDTO;
import xyz.markpost.bankdemo.model.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.TransactionService;

@SwaggerDefinition(
    tags = {
        @Tag(name = "Accounts", description = "API request options related to account entities")
    }
)

/**
 * REST controller for account entity and its'relations
 */
@RestController
@RequestMapping("v1/accounts")
@Api(tags = {"Accounts"})
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  /**
   * REST API call for creating an account
   * TODO: add AccountRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param accountRequestDTO DTO containing data for new account entity
   * @return The response DTO of the created account entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDTO createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.create(accountRequestDTO);
  }

  /**
   * REST API call for retrieving certain account or all accounts
   * TODO: add option for finding set of accounts (input list of id's)
   * TODO: swagger annotation
   *
   * @param accountId Account to retrieve (not required)
   * @return List of found accounts
   */
  @GetMapping(path = "{accountId}", produces = "application/json")
  public List<AccountResponseDTO> retrieveAccount(
      @PathVariable(value = "accountId", required = false) Long accountId) {
    if (null != accountId) {
      return accountService.findById(accountId);
    } else {
      return accountService.findAll();
    }
  }

  /**
   * Get all transactions of given account
   * TODO: swagger annotation
   *
   * @param accountId The id of the account to get the transactions of
   * @return The list of transactions of the account
   */
  @GetMapping(path = "{accountId}/transactions", produces = "application/json")
  public List<TransactionResponseDTO> retrieveAccountTransactions(
      @PathVariable(value = "accountId") Long accountId) {
    List<TransactionResponseDTO> transactionResponseDTOS = transactionService
        .findByAccountId(accountId);

    transactionResponseDTOS.sort(new SortByDate());

    return transactionResponseDTOS;
  }

  /**
   * Update given account
   * TODO: add AccountRequestDTO validation (custom annotation?)
   * TODO: swagger
   * annotation
   *
   * @param accountId The id of the account to update
   * @param accountRequestDTO The data of the to update fields
   * @return The updated account
   */
  @PatchMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public AccountResponseDTO updateAccount(@PathVariable("accountId") Long accountId,
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.update(accountId, accountRequestDTO);
  }

  /**
   * Delete the account with the given id
   * TODO: swagger annotation
   *
   * @param accountId The id of the account to delete
   */
  @DeleteMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(@PathVariable("accountId") Long accountId) {
    accountService.delete(accountId);
  }

}
