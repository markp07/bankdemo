package xyz.markpost.bankdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.Transaction;
import xyz.markpost.bankdemo.model.TransactionRequestDTO;
import xyz.markpost.bankdemo.model.TransactionResponseDTO;
import xyz.markpost.bankdemo.model.TransactionType;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.TransactionRepository;


@Service
@Transactional
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  /**
   * TODO: check requestDTO
   */
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
      throw new EntityNotFoundException("Account with id " + account.getId() + " not found.");
    } else if (null != contraAccount) {
      throw new EntityNotFoundException("Account with id " + contraAccount.getId() + " not found.");
    } else {
      throw new EntityNotFoundException(
          "Account with id " + account.getId() + " not found. Account with id " + contraAccount
              .getId() + " not found.");

    }
  }

  /**
   *
   * @param transactionId
   * @return
   */
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
  public List<TransactionResponseDTO> findByAccountId(Long accountId) {
    Account account = findSingleAccount(accountId);

    if (null != account) {
      List<Transaction> transactions = account.getTransactions();
      transactions.addAll(account.getContraTransactions());

      ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

      //TODO: use stream and sort by date
      transactions.forEach(transaction -> {
        TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
        transactionResponseDTOS.add(transactionResponseDTO);
      });

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
  public List<TransactionResponseDTO> findAll() {
    Iterable<Transaction> transactions = transactionRepository.findAll();
    ArrayList<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    transactions.forEach(transaction -> {
      TransactionResponseDTO transactionResponseDTO = createResponseTransaction(transaction);
      transactionResponseDTOS.add(transactionResponseDTO);
    });

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
