package xyz.markpost.bankdemo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
 *
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;

  private final TransactionRepository transactionRepository;

  /**
   *
   * @param accountRepository
   * @param transactionRepository
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
   * TODO: check requestDTO
   * @param transactionRequestDTO
   * @return
   */
  @Override
  public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
    Transaction transaction = new Transaction();
    Account account = findSingleAccount(transactionRequestDTO.getAccountId());
    Account contraAccount = findSingleAccount(transactionRequestDTO.getContraAccountId());

    if (null != account && null != contraAccount) {
      transaction.setAccount(account);
      transaction.setAmount(transactionRequestDTO.getAmount());
      transaction.setContraAccount(contraAccount);
      transaction.setDate(transactionRequestDTO.getDate());
      transaction.setDescription(transactionRequestDTO.getDescription());
      transaction.setType(transactionRequestDTO.getType());

      transaction = transactionRepository.save(transaction);

      if (TransactionType.DEPOSIT == transaction.getType()) {
        float currentBalance = account.getBalance();
        float newBalance = currentBalance + transaction.getAmount();
        account.setBalance(newBalance);

        currentBalance = contraAccount.getBalance();
        newBalance = currentBalance - transaction.getAmount();
        contraAccount.setBalance(newBalance);
      } else if (TransactionType.WITHDRAWAL == transaction.getType()) {
        float currentBalance = account.getBalance();
        float newBalance = currentBalance - transaction.getAmount();
        account.setBalance(newBalance);

        currentBalance = contraAccount.getBalance();
        newBalance = currentBalance + transaction.getAmount();
        contraAccount.setBalance(newBalance);
      }

      return createResponseTransaction(transaction);
    } else if (null != account) {
      throw new EntityNotFoundException("Account with id " +transactionRequestDTO.getAccountId() + " not found.");
    } else if (null != contraAccount) {
      throw new EntityNotFoundException("Account with id " + transactionRequestDTO.getContraAccountId() + " not found.");
    } else {
      throw new EntityNotFoundException(
          "Account with id " + transactionRequestDTO.getAccountId() + " not found. Account with id " + transactionRequestDTO.getContraAccountId() + " not found.");

    }
  }

  /**
   *
   * @param transactionId
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findById(Long transactionId) {
    Transaction account = findSingleTransaction(transactionId);
    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    if (null != account) {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(account);
      transactionResponseDTOS.add(transactionResponseDTO);
    }

    return transactionResponseDTOS;
  }


  /**
   *
   * @param accountId
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findByAccountId(Long accountId) {
    Account account = findSingleAccount(accountId);

    if (null != account) {
      List<Transaction> transactions = account.getTransactions();
      transactions.addAll(account.getContraTransactions());

      ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

      transactions.forEach(transaction -> {
        TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
        transactionResponseDTOS.add(transactionResponseDTO);
      });

      transactionResponseDTOS.sort(new TransactionSortByDate());

      return transactionResponseDTOS;
    } else {
      throw new EntityNotFoundException(
          "Account with accountId " + accountId.toString() + " not found.");
    }
  }

  /**
   *
   * @return
   */
  @Override
  public List<TransactionResponseDTO> findAll() {
    Iterable<Transaction> transactions = transactionRepository.findAll();
    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
      transactionResponseDTOS.add(transactionResponseDTO);
    });

    transactionResponseDTOS.sort(new TransactionSortByDate());

    return transactionResponseDTOS;
  }

  /**
   *
   * @param transactionId
   * @return
   */
  private Transaction findSingleTransaction(Long transactionId) {
    Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);

    return transactionOptional.orElse(null);
  }

  /**
   *
   * @param accountId
   * @return
   */
  private Account findSingleAccount(Long accountId) {
    Optional<Account> accountOptional = accountRepository.findById(accountId);

    return accountOptional.orElse(null);
  }

  /**
   *
   * @param transaction
   * @return
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
