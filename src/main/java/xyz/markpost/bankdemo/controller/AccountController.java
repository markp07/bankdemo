package xyz.markpost.bankdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import xyz.markpost.bankdemo.dto.AccountRequestDTO;
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

/**
 * REST controller for account entity and its relations
 */
@RestController
@RequestMapping("v1/accounts")
@Tag(name = "Accounts", description = "API request options related to account entities")
public class AccountController {

  private final AccountService accountService;
  private final TransactionService transactionService;

  @Autowired
  public AccountController(AccountService accountService, TransactionService transactionService) {
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  /**
   * REST API call for creating an account
   *
   * @param accountRequestDTO DTO containing data for new account entity
   * @return The response DTO of the created account entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a new account", description = "Creates a new account with the provided details")
  public AccountResponseDTO createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.create(accountRequestDTO);
  }

  /**
   * REST API call for retrieving certain account or all accounts
   *
   * @param accountId Account to retrieve (not required)
   * @return List of found accounts
   */
  @GetMapping(path = "{accountId}", produces = "application/json")
  @Operation(summary = "Retrieve account(s)", description = "Retrieves a specific account by ID or all accounts if no ID is provided")
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
   *
   * @param accountId The id of the account to get the transactions of
   * @return The list of transactions of the account
   */
  @GetMapping(path = "{accountId}/transactions", produces = "application/json")
  @Operation(summary = "Retrieve account transactions", description = "Retrieves all transactions for a specific account by ID")
  public List<TransactionResponseDTO> retrieveAccountTransactions(
      @PathVariable(value = "accountId") Long accountId) {
    List<TransactionResponseDTO> transactionResponseDTOS = transactionService.findByAccountId(
        accountId);
    transactionResponseDTOS.sort(new TransactionSortByDate());
    return transactionResponseDTOS;
  }

  /**
   * Update given account
   *
   * @param accountId         The id of the account to update
   * @param accountRequestDTO The data of the to update fields
   * @return The updated account
   */
  @PatchMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update an account", description = "Updates the details of an existing account by ID")
  public AccountResponseDTO updateAccount(@PathVariable("accountId") Long accountId,
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.update(accountId, accountRequestDTO);
  }

  /**
   * Delete the account with the given id
   *
   * @param accountId The id of the account to delete
   */
  @DeleteMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete an account", description = "Deletes an existing account by ID")
  public void deleteAccount(@PathVariable("accountId") Long accountId) {
    accountService.delete(accountId);
  }
}