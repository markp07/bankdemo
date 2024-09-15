package xyz.markpost.bankdemo.service;

import java.util.List;
import xyz.markpost.bankdemo.dto.TransactionRequestDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;

/**
 * Service interface for managing transactions.
 */
public interface TransactionService {

  /**
   * Creates a new transaction.
   *
   * @param transactionRequestDTO DTO containing data for the new transaction entity.
   * @return The response DTO of the created transaction entity.
   */
  TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

  /**
   * Finds transactions by their ID.
   *
   * @param transactionId The ID of the transaction to retrieve.
   * @return A list of found transaction response DTOs.
   */
  List<TransactionResponseDTO> findById(Long transactionId);

  /**
   * Finds transactions by account ID.
   *
   * @param accountId The ID of the account to retrieve transactions for.
   * @return A list of found transaction response DTOs.
   */
  List<TransactionResponseDTO> findByAccountId(Long accountId);

  /**
   * Retrieves all transactions.
   *
   * @return A list of all transaction response DTOs.
   */
  List<TransactionResponseDTO> findAll();

  /**
   * Finds transactions by a list of IDs.
   *
   * @param transactionIds A list of transaction IDs to retrieve.
   * @return A list of found transaction response DTOs.
   */
  List<TransactionResponseDTO> findByIds(List<Long> transactionIds);
}