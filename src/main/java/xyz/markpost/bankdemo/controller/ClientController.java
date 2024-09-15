package xyz.markpost.bankdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * REST controller for client entity and its'relations
 */
@RestController
@RequestMapping("v1/clients")
@Tag(name = "Clients", description = "API request options related to client entities")
public class ClientController {

  private final ClientService clientService;
  private final AccountService accountService;
  private final TransactionService transactionService;

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
   * REST API call for creating a client
   *
   * @param clientRequestDTO DTO containing data for new client entity
   * @return The response DTO of the created client entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a new client", description = "Creates a new client with the provided details")
  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.create(clientRequestDTO);
  }

  /**
   * REST API call for retrieving certain client or all clients
   *
   * @param clientId Client to retrieve (not required)
   * @return List of found clients
   */
  @GetMapping(path = "{clientId}", produces = "application/json")
  @Operation(summary = "Retrieve client(s)", description = "Retrieves a specific client by ID or all clients if no ID is provided")
  public List<ClientResponseDTO> retrieveClient(
      @PathVariable(value = "clientId", required = false) Long clientId) {
    if (null != clientId) {
      return clientService.findById(clientId);
    } else {
      return clientService.findAll();
    }
  }

  /**
   * Get all accounts of given client
   *
   * @param clientId The id of the client to get the accounts of
   * @return The list of accounts of the client
   */
  @GetMapping(path = "{clientId}/accounts", produces = "application/json")
  @Operation(summary = "Retrieve client accounts", description = "Retrieves all accounts for a specific client by ID")
  public List<AccountResponseDTO> retrieveClientAccounts(
      @PathVariable(value = "clientId") Long clientId) {
    return accountService.findByClientId(clientId);
  }

  /**
   * Get all transactions of given client
   *
   * @param clientId The id of the client to get the transactions of
   * @return The list of transactions of the client
   */
  @GetMapping(path = "{clientId}/transactions", produces = "application/json")
  @Operation(summary = "Retrieve client transactions", description = "Retrieves all transactions for a specific client by ID")
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
   * Update given client
   *
   * @param clientId The id of the client to update
   * @param clientRequestDTO The data of the to update fields
   * @return The updated client
   */
  @PatchMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update a client", description = "Updates the details of an existing client by ID")
  public ClientResponseDTO updateClient(@PathVariable("clientId") Long clientId,
      @RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.update(clientId, clientRequestDTO);
  }

  /**
   * Delete the client with the given id
   *
   * @param clientId The id of the client to delete
   */
  @DeleteMapping(path = "{clientId}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete a client", description = "Deletes an existing client by ID")
  public void deleteClient(@PathVariable("clientId") Long clientId) {
    clientService.delete(clientId);
  }
}