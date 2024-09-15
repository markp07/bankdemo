package xyz.markpost.bankdemo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.bankdemo.dto.TransactionRequestDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.Transaction;
import xyz.markpost.bankdemo.model.TransactionType;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.TransactionRepository;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

/**
 * Service implementation for managing transactions.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  /**
   * Constructor for TransactionServiceImpl.
   *
   * @param accountRepository the account repository
   * @param transactionRepository the transaction repository
   */
  @Autowired
  public TransactionServiceImpl(
      AccountRepository accountRepository,
      TransactionRepository transactionRepository
  ) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  /**
   * Creates a new transaction.
   *
   * @param transactionRequestDTO DTO containing data for the new transaction entity.
   * @return The response DTO of the created transaction entity.
   */
  @Override
  public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
    Transaction transaction = new Transaction();
    Account account = findSingleAccount(transactionRequestDTO.getAccountId());
    Account contraAccount = findSingleAccount(transactionRequestDTO.getContraAccountId());

    if (account != null && contraAccount != null) {
      transaction.setAccount(account);
      transaction.setAmount(transactionRequestDTO.getAmount());
      transaction.setContraAccount(contraAccount);
      transaction.setDate(transactionRequestDTO.getDate());
      transaction.setDescription(transactionRequestDTO.getDescription());
      transaction.setType(transactionRequestDTO.getType());

      transaction = transactionRepository.save(transaction);

      if (TransactionType.DEPOSIT == transaction.getType()) {
        account.setBalance(account.getBalance() + transaction.getAmount());
        contraAccount.setBalance(contraAccount.getBalance() - transaction.getAmount());
      } else if (TransactionType.WITHDRAWAL == transaction.getType()) {
        account.setBalance(account.getBalance() - transaction.getAmount());
        contraAccount.setBalance(contraAccount.getBalance() + transaction.getAmount());
      }

      return createResponseTransaction(transaction);
    } else if (account == null) {
      throw new EntityNotFoundException("Account with id " + transactionRequestDTO.getAccountId() + " not found.");
    } else {
      throw new EntityNotFoundException("Account with id " + transactionRequestDTO.getContraAccountId() + " not found.");
    }
  }

  /**
   * Finds transactions by their ID.
   *
   * @param transactionId The ID of the transaction to retrieve.
   * @return A list of found transaction response DTOs.
   */
  @Override
  public List<TransactionResponseDTO> findById(Long transactionId) {
    Transaction transaction = findSingleTransaction(transactionId);
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    if (transaction != null) {
      transactionResponseDTOS.add(createResponseTransaction(transaction));
    }

    return transactionResponseDTOS;
  }

  /**
   * Finds transactions by account ID.
   *
   * @param accountId The ID of the account to retrieve transactions for.
   * @return A list of found transaction response DTOs.
   */
  @Override
  public List<TransactionResponseDTO> findByAccountId(Long accountId) {
    Account account = findSingleAccount(accountId);

    if (account != null) {
      List<Transaction> transactions = new ArrayList<>();
      transactions.addAll(account.getTransactions());
      transactions.addAll(account.getContraTransactions());

      List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();
      transactions.forEach(transaction -> transactionResponseDTOS.add(createResponseTransaction(transaction)));
      transactionResponseDTOS.sort(new TransactionSortByDate());

      return transactionResponseDTOS;
    } else {
      throw new EntityNotFoundException("Account with accountId " + accountId + " not found.");
    }
  }

  /**
   * Retrieves all transactions.
   *
   * @return A list of all transaction response DTOs.
   */
  @Override
  public List<TransactionResponseDTO> findAll() {
    Iterable<Transaction> transactions = transactionRepository.findAll();
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> transactionResponseDTOS.add(createResponseTransaction(transaction)));
    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   * Finds transactions by a list of IDs.
   *
   * @param transactionIds A list of transaction IDs to retrieve.
   * @return A list of found transaction response DTOs.
   */
  @Override
  public List<TransactionResponseDTO> findByIds(List<Long> transactionIds) {
    Iterable<Transaction> transactionsIterable = transactionRepository.findAllById(transactionIds);
    List<Transaction> transactions = new ArrayList<>();
    transactionsIterable.forEach(transactions::add);
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> transactionResponseDTOS.add(createResponseTransaction(transaction)));
    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   * Finds a single transaction by its ID.
   *
   * @param transactionId The ID of the transaction to retrieve.
   * @return The found transaction or null if not found.
   */
  private Transaction findSingleTransaction(Long transactionId) {
    Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
    return transactionOptional.orElse(null);
  }

  /**
   * Finds a single account by its ID.
   *
   * @param accountId The ID of the account to retrieve.
   * @return The found account or null if not found.
   */
  private Account findSingleAccount(Long accountId) {
    Optional<Account> accountOptional = accountRepository.findById(accountId);
    return accountOptional.orElse(null);
  }

  /**
   * Creates a response DTO from a transaction entity.
   *
   * @param transaction The transaction entity.
   * @return The response DTO.
   */
  private TransactionResponseDTO createResponseTransaction(Transaction transaction) {
    TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
    transactionResponseDTO.setId(transaction.getId());
    transactionResponseDTO.setAccountId(transaction.getAccount().getId());
    transactionResponseDTO.setAccountName(transaction.getAccount());
    transactionResponseDTO.setContraAccountId(transaction.getContraAccount().getId());
    transactionResponseDTO.setContraAccountName(transaction.getContraAccount());
    transactionResponseDTO.setType(transaction.getType());
    transactionResponseDTO.setDate(transaction.getDate());
    transactionResponseDTO.setAmount(transaction.getAmount());
    transactionResponseDTO.setDescription(transaction.getDescription());
    return transactionResponseDTO;
  }
}