package xyz.markpost.bankdemo.controller;

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
import xyz.markpost.bankdemo.service.AccountService;


@RestController
@RequestMapping("v1/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

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
   * @param id
   * @return
   */
  @GetMapping(path = "id", produces = "application/json")
  public List<AccountResponseDTO> retrieveAccount(
      @PathVariable(value = "id", required = false) Long id) {
    if (null != id) {
      return accountService.findById(id);
    } else {
      return accountService.findAll();
    }
  }

  /**
   *
   * @param id
   * @param accountRequestDTO
   * @return
   */
  @PatchMapping(path = "{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public AccountResponseDTO updateAccount(@PathVariable("id") Long id,
      @RequestBody AccountRequestDTO accountRequestDTO) {
    return accountService.update(id, accountRequestDTO);
  }

  /**
   *
   * @param id
   */
  @DeleteMapping(path = "{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(@PathVariable("id") Long id) {
    accountService.delete(id);
  }

}
