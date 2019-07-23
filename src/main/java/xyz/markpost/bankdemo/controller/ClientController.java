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
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.dto.ClientRequestDTO;
import xyz.markpost.bankdemo.dto.ClientResponseDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.ClientService;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;


@SwaggerDefinition(
    tags = {
        @Tag(name = "Clients", description = "API request options related to client entities")
    }
)

/**
 * REST controller for client entity and its'relations
 */
@RestController
@RequestMapping("v1/clients")
@Api(tags = {"Clients"})
public class ClientController {

  private ClientService clientService;

  private AccountService accountService;

  private TransactionService transactionService;

  @Autowired
  public ClientController(
      ClientService clientService,
      AccountService accountService,
      TransactionService transactionService
  ) {
    this.clientService = clientService;
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  /**
   * REST API call for creating an client TODO: add ClientRequestDTO validation (custom annotation?)
   * TODO: swagger annotation
   *
   * @param clientRequestDTO DTO containing data for new client entity
   * @return The response DTO of the created client entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.create(clientRequestDTO);
  }

  /**
   * REST API call for retrieving certain client or all clients TODO: add option for finding set of
   * clients (input list of id's) TODO: swagger annotation
   *
   * @param clientId Client to retrieve (not required)
   * @return List of found clients
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
   * Get all accounts of given client TODO: swagger annotation
   *
   * @param clientId The id of the client to get the accounts of
   * @return The list of accounts of the client
   */
  @GetMapping(path = "{clientId}/accounts", produces = "application/json")
  public List<AccountResponseDTO> retrieveClientAccounts(
      @PathVariable(value = "clientId") Long clientId) {
    return accountService.findByClientId(clientId);
  }

  /**
   * Get all transactions of given client TODO: swagger annotation
   *
   * @param clientId The id of the client to get the transactions of
   * @return The list of transactions of the client
   */
  @GetMapping(path = "{clientId}/transactions", produces = "application/json")
  public List<TransactionResponseDTO> retrieveClientAccountsTransactions(
      @PathVariable(value = "clientId") Long clientId) {
    List<AccountResponseDTO> accountResponseDTOS = accountService.findByClientId(clientId);
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    accountResponseDTOS.forEach(accountResponseDTO -> {
      List<TransactionResponseDTO> transactions = transactionService
          .findByAccountId(accountResponseDTO.getId());
      transactionResponseDTOS.addAll(transactions);
    });

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   * Update given client TODO: add clientRequestDTO validation (custom annotation?) TODO: swagger
   * annotation
   *
   * @param clientId The id of the client to update
   * @param clientRequestDTO The data of the to update fields
   * @return The updated client
   */
  @PatchMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ClientResponseDTO updateClient(@PathVariable("clientId") Long clientId,
      @RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.update(clientId, clientRequestDTO);
  }

  /**
   * Delete the client with the given id TODO: swagger annotation
   *
   * @param clientId The id of the client to delete
   */
  @DeleteMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClient(@PathVariable("clientId") Long clientId) {
    clientService.delete(clientId);
  }

}


