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

@SwaggerDefinition(
    tags = {
        @Tag(name = "Transactions", description = "API request options related to transaction entities")
    }
)

@RestController
@RequestMapping("v1/transactions")
@Api(tags = {"Transactions"})
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  /**
   * TODO: check requestDTO
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponseDTO createTransaction(
      @RequestBody TransactionRequestDTO transactionRequestDTO) {
    return transactionService.create(transactionRequestDTO);
  }

  /**
   *
   * @param transactionId
   * @return
   */
  @GetMapping(path = "transactionId", produces = "application/json")
  public List<TransactionResponseDTO> retrieveTransaction(
      @PathVariable(value = "transactionId", required = false) Long transactionId) {
    if (null != transactionId) {
      return transactionService.findById(transactionId);
    } else {
      return transactionService.findAll();
    }
  }

}
