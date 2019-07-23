package xyz.markpost.bankdemo.service;

import java.util.List;
import xyz.markpost.bankdemo.dto.TransactionRequestDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;

/**
 *
 */
public interface TransactionService {

  /**
   *
   * @param transactionRequestDTO
   * @return
   */
  TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

  /**
   *
   * @param transactionId
   * @return
   */
  List<TransactionResponseDTO> findById(Long transactionId);

  /**
   *
   * @param accountId
   * @return
   */
  List<TransactionResponseDTO> findByAccountId(Long accountId);

  /**
   *
   * @return
   */
  List<TransactionResponseDTO> findAll();

}
