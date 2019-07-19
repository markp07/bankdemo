package xyz.markpost.bankdemo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.ClientService;
import xyz.markpost.bankdemo.service.TransactionService;

@ExtendWith(SpringExtension.class)
class ClientControllerUT {

  @Mock
  private ClientService clientService;

  @Mock
  private AccountService accountService;

  @Mock
  private TransactionService transactionService;
  
  @InjectMocks
  ClientController clientController;

  @BeforeAll
  static void init() {

  }

  @Test
  @DisplayName("TODO")
  void createClientTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveClientWithIdTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveClientWithoutIdTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveClientAccountsTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveClientAccountsTransactionsTest() {

  }

  @Test
  @DisplayName("TODO")
  void updateClientTest() {

  }

  @Test
  @DisplayName("TODO")
  void deleteClientTest() {

  }

  }