package xyz.markpost.bankdemo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.dto.TransactionRequestDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.model.Transaction;
import xyz.markpost.bankdemo.model.TransactionType;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  private TransactionRequestDTO transactionRequestDTO;
  private Client client;
  private Account account;
  private Account contraAccount;
  private Transaction transaction;

  @BeforeEach
  void setUp() {
    transactionRequestDTO = new TransactionRequestDTO();
    transactionRequestDTO.setAccountId(1L);
    transactionRequestDTO.setContraAccountId(2L);
    transactionRequestDTO.setAmount(100.0f);
    transactionRequestDTO.setDate(Date.valueOf("2021-01-01"));
    transactionRequestDTO.setDescription("Test Transaction");
    transactionRequestDTO.setType(TransactionType.DEPOSIT);

    client = new Client();
    client.setId(1);
    client.setFirstName("Foo");
    client.setLastName("Bar");
    client.setBirthDate(new Date(0));
    client.setAddress("Test Address 1");

    account = new Account();
    account.setId(1L);
    account.setBalance(1000.0f);
    account.setClient(client);

    contraAccount = new Account();
    contraAccount.setId(2L);
    contraAccount.setBalance(2000.0f);
    contraAccount.setClient(client);

    transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAccount(account);
    transaction.setContraAccount(contraAccount);
    transaction.setAmount(100.0f);
    transaction.setDate(Date.valueOf("2021-01-01"));
    transaction.setDescription("Test Transaction");
    transaction.setType(TransactionType.DEPOSIT);

    account.setTransactions(List.of(transaction));
    account.setContraTransactions(List.of(transaction));
    contraAccount.setTransactions(List.of(transaction));
    contraAccount.setContraTransactions(List.of(transaction));
  }

  @Test
  void createTransactionSuccess() {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.findById(2L)).thenReturn(Optional.of(contraAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    TransactionResponseDTO result = transactionService.create(transactionRequestDTO);

    assertNotNull(result);
    assertEquals(1L, result.getAccountId());
    assertEquals(2L, result.getContraAccountId());
    assertEquals(100.0f, result.getAmount());
  }

  @Test
  void createTransactionAccountNotFound() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> transactionService.create(transactionRequestDTO));
  }

  @Test
  void createTransactionContraAccountNotFound() {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> transactionService.create(transactionRequestDTO));
  }

  @Test
  void findByIdSuccess() {
    when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

    List<TransactionResponseDTO> result = transactionService.findById(1L);

    assertFalse(result.isEmpty());
    assertEquals(1L, result.get(0).getId());
  }

  @Test
  void findByIdTransactionNotFound() {
    when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

    List<TransactionResponseDTO> result = transactionService.findById(1L);

    assertTrue(result.isEmpty());
  }

  @Test
  void findByAccountIdSuccess() {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
    when(transactionRepository.findAll()).thenReturn(List.of(transaction));

    List<TransactionResponseDTO> result = transactionService.findByAccountId(1L);

    assertFalse(result.isEmpty());
    assertEquals(1L, result.get(0).getAccountId());
  }

  @Test
  void findByAccountIdAccountNotFound() {
    when(accountRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> transactionService.findByAccountId(1L));
  }

  @Test
  void findAllTransactions() {
    when(transactionRepository.findAll()).thenReturn(List.of(transaction));

    List<TransactionResponseDTO> result = transactionService.findAll();

    assertFalse(result.isEmpty());
    assertEquals(1L, result.get(0).getId());
  }
}