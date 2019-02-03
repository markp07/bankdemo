package xyz.markpost.bankdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.ArrayList;
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
import xyz.markpost.bankdemo.model.AccountResponseDTO;
import xyz.markpost.bankdemo.model.ClientRequestDTO;
import xyz.markpost.bankdemo.model.ClientResponseDTO;
import xyz.markpost.bankdemo.model.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.ClientService;
import xyz.markpost.bankdemo.service.TransactionService;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Clients", description = "API request options related to client entities")
    }
)

@RestController
@RequestMapping("v1/clients")
@Api(tags = {"Clients"})
public class ClientController {

  @Autowired
  private ClientService clientService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  /**
   *
   * @param clientRequestDTO
   * @return
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.create(clientRequestDTO);
  }

  /**
   *
   * @param clientId
   * @return
   */
  @GetMapping(path = "{clientId}", produces = "application/json")
  public List<ClientResponseDTO> retrieveClient(
      @PathVariable(value = "clientId", required = false) Long clientId) {
    if (null != clientId) {
      return clientService.findById(clientId);
    } else {
      return clientService.findAll();
    }
  }

  /**
   *
   * @param clientId
   * @return
   */
  @GetMapping(path = "{clientId}/accounts", produces = "application/json")
  public List<AccountResponseDTO> retrieveClientAccounts(
      @PathVariable(value = "clientId") Long clientId) {
    return accountService.findByClientId(clientId);
  }

  /**
   *
   * @param clientId
   * @return
   */
  @GetMapping(path = "{clientId}/accounts/transactions", produces = "application/json")
  public List<TransactionResponseDTO> retrieveClientAccountsTransactions(
      @PathVariable(value = "clientId") Long clientId) {
    List<AccountResponseDTO> accountResponseDTOS = accountService.findByClientId(clientId);
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    accountResponseDTOS.forEach(accountResponseDTO -> {
      List<TransactionResponseDTO> transactions = transactionService
          .findByAccountId(accountResponseDTO.getId());
      transactionResponseDTOS.addAll(transactions);
    });

    return transactionResponseDTOS;
  }

  /**
   *
   * @param clientId
   * @param clientRequestDTO
   * @return
   */
  @PatchMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ClientResponseDTO updateClient(@PathVariable("clientId") Long clientId,
      @RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.update(clientId, clientRequestDTO);
  }

  /**
   *
   * @param clientId
   */
  @DeleteMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClient(@PathVariable("clientId") Long clientId) {
    clientService.delete(clientId);
  }

}