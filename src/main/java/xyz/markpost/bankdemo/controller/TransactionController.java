package xyz.markpost.bankdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
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
import xyz.markpost.bankdemo.model.TransactionRequestDTO;
import xyz.markpost.bankdemo.model.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

@SwaggerDefinition(
    tags = {
        @Tag(name = "Transactions", description = "API request options related to transaction entities")
    }
)

/**
 * REST controller for transaction entity
 */
@RestController
@RequestMapping("v1/transactions")
@Api(tags = {"Transactions"})
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  /**
   * REST API call for creating an transaction TODO: add TransactionRequestDTO validation (custom
   * annotation?) TODO: swagger annotation
   *
   * @param transactionRequestDTO DTO containing data for new transaction entity
   * @return The response DTO of the created transaction entity
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponseDTO createTransaction(
      @RequestBody TransactionRequestDTO transactionRequestDTO) {
    return transactionService.create(transactionRequestDTO);
  }

  /**
   * REST API call for retrieving certain transaction or all transactions TODO: add option for
   * finding set of transactions (input list of id's) TODO: swagger annotation
   *
   * @param transactionId Transaction to retrieve (not required)
   * @return List of found transactions
   */
  @GetMapping(path = "{transactionId}", produces = "application/json")
  public List<TransactionResponseDTO> retrieveTransaction(
      @PathVariable(value = "transactionId", required = false) Long transactionId) {
    List<TransactionResponseDTO> transactionResponseDTOS;
    if (null != transactionId) {
      transactionResponseDTOS = transactionService.findById(transactionId);
    } else {
      transactionResponseDTOS = transactionService.findAll();
    }

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;

  }

}
