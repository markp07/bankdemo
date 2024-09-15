package xyz.markpost.bankdemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.dto.AccountRequestDTO;
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

@ExtendWith(SpringExtension.class)
class AccountControllerTest {

  @Mock
  private AccountService accountService;

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  AccountController accountController;

  @Test
  @DisplayName("Test creating an account")
  void createAccountTest() {
    AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    when(accountService.create(accountRequestDTO)).thenReturn(accountResponseDTO);

    AccountResponseDTO result = accountController.createAccount(accountRequestDTO);

    assertThat(result).isEqualTo(accountResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving an account")
  void retrieveAccountWithIdTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO transactionResponseDTO = new AccountResponseDTO();
    accountResponseDTOS.add(transactionResponseDTO);
    when(accountService.findById(any())).thenReturn(accountResponseDTOS);

    List<AccountResponseDTO> result = accountController.retrieveAccount(1L);

    assertThat(result)
        .hasSize(1)
        .isEqualTo(accountResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieving all accounts")
  void retrieveAccountWithoutIdTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO accountResponseDTOA = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOA);

    AccountResponseDTO accountResponseDTOB = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOB);

    AccountResponseDTO accountResponseDTOC = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOC);

    when(accountService.findAll()).thenReturn(accountResponseDTOS);

    List<AccountResponseDTO> result = accountController.retrieveAccount(null);

    assertThat(result)
        .hasSize(3)
        .isEqualTo(accountResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieving all transactions of an account")
  void retrieveAccountTransactionsTest() {
    List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTOA = new TransactionResponseDTO();
    transactionResponseDTOA.setDate(new Date(0));
    transactionResponseDTOS.add(transactionResponseDTOA);

    TransactionResponseDTO transactionResponseDTOB = new TransactionResponseDTO();
    transactionResponseDTOB.setDate(new Date(172800000));
    transactionResponseDTOS.add(transactionResponseDTOB);

    TransactionResponseDTO transactionResponseDTOC = new TransactionResponseDTO();
    transactionResponseDTOC.setDate(new Date(86400000));
    transactionResponseDTOS.add(transactionResponseDTOC);

    when(transactionService.findByAccountId(any())).thenReturn(transactionResponseDTOS);

    List<TransactionResponseDTO> result = accountController.retrieveAccountTransactions(1L);

    assertThat(result)
        .hasSize(3)
        .isSortedAccordingTo(new TransactionSortByDate())
        .isEqualTo(transactionResponseDTOS);
  }

  @Test
  @DisplayName("Test updating an account")
  void updateAccountTest() {
    AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    when(accountService.update(any(), any(AccountRequestDTO.class))).thenReturn(accountResponseDTO);

    AccountResponseDTO result = accountController.updateAccount(1L, accountRequestDTO);

    assertThat(result).isEqualTo(accountResponseDTO);
  }

  @Test
  @DisplayName("Test deleting an account")
  void deleteAccountTest() {
    accountController.deleteAccount(1L);
    //We cannot assert anything here
  }

}