package xyz.markpost.bankdemo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import xyz.markpost.bankdemo.service.TransactionService;

class TransactionControllerUT {

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  TransactionController transactionController;

  @BeforeAll
  static void init() {

  }

  @Test
  @DisplayName("TODO")
  void createTransactionTest() {

  }

  @Test
  @DisplayName("TODO")
  void retrieveTransactionTest() {

  }
}