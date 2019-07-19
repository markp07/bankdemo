package xyz.markpost.bankdemo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.TransactionService;

@ExtendWith(SpringExtension.class)
class AccountControllerUT {

  @Mock
  private AccountService accountService;

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  AccountController accountController;

  @BeforeAll
  static void init() {

  }

  @Test
  @DisplayName("TODO")
  void createAccountTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveAccountWithIdTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveAccountWithoutIdTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveAccountTransactionsTest() {

  }

  @Test
  @DisplayName("TODO")
  void updateAccountTest() {

  }

  @Test
  @DisplayName("TODO")
  void deleteAccountTest() {

  }

}