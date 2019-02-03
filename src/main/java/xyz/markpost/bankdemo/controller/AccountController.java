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

@RestController
@RequestMapping("v1/accounts")
@Api(tags = {"Accounts"})
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  /**
   *
   * @param accountRequestDTO
   * @return
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDTO createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.create(accountRequestDTO);
  }

  /**
   *
   * @param accountId
   * @return
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
   *
   * @param accountId
   * @return
   */
  @GetMapping(path = "{accountId}/transactions", produces = "application/json")
  public List<TransactionResponseDTO> retrieveAccountTransactions(
      @PathVariable(value = "accountId") Long accountId) {
    return transactionService.findByAccountId(accountId);
  }

  /**
   *
   * @param accountId
   * @param accountRequestDTO
   * @return
   */
  @PatchMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public AccountResponseDTO updateAccount(@PathVariable("accountId") Long accountId,
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.update(accountId, accountRequestDTO);
  }

  /**
   *
   * @param accountId
   */
  @DeleteMapping(path = "{accountId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(@PathVariable("accountId") Long accountId) {
    accountService.delete(accountId);
  }

}
