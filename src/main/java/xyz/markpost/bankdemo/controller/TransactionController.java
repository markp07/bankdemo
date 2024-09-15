package xyz.markpost.bankdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.bankdemo.dto.TransactionRequestDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

/**
 * REST controller for transaction entity
 */
@RestController
@RequestMapping("v1/transactions")
@Tag(name = "Transactions", description = "API request options related to transaction entities")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  /**
   * REST API call for creating a transaction
   *
   * @param transactionRequestDTO DTO containing data for new transaction entity
   * @return The response DTO of the created transaction entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a new transaction", description = "Creates a new transaction with the provided details")
  public TransactionResponseDTO createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
    return transactionService.create(transactionRequestDTO);
  }

  /**
   * REST API call for retrieving certain transaction or all transactions
   *
   * @param transactionId Transaction to retrieve (not required)
   * @return List of found transactions
   */
  @GetMapping(path = "{transactionId}", produces = "application/json")
  @Operation(summary = "Retrieve transaction(s)", description = "Retrieves a specific transaction by ID or all transactions if no ID is provided")
  public List<TransactionResponseDTO> retrieveTransaction(@PathVariable(value = "transactionId", required = false) Long transactionId) {
    List<TransactionResponseDTO> transactionResponseDTOS;
    if (null != transactionId) {
      transactionResponseDTOS = transactionService.findById(transactionId);
    } else {
      transactionResponseDTOS = transactionService.findAll();
    }

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   * REST API call for retrieving a set of transactions by their IDs
   *
   * @param transactionIds List of transaction IDs to retrieve
   * @return List of found transactions
   */
  @GetMapping(path = "batch", produces = "application/json")
  @Operation(summary = "Retrieve a set of transactions", description = "Retrieves a set of transactions by their IDs")
  public List<TransactionResponseDTO> retrieveTransactionsByIds(@RequestBody List<Long> transactionIds) {
    List<TransactionResponseDTO> transactionResponseDTOS = transactionService.findByIds(transactionIds);
    transactionResponseDTOS.sort(new TransactionSortByDate());
    return transactionResponseDTOS;
  }
}