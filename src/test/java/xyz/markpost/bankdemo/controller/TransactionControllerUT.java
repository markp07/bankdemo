package xyz.markpost.bankdemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.model.TransactionRequestDTO;
import xyz.markpost.bankdemo.model.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.TransactionService;

@ExtendWith(SpringExtension.class)
class TransactionControllerUT {

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  TransactionController transactionController = new TransactionController();

  private TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
  private TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
  private List<TransactionResponseDTO> transactionResponseDTOSWithId = new ArrayList<>();
  private List<TransactionResponseDTO> transactionResponseDTOSWithoutId = new ArrayList<>();


  @BeforeEach
  void init() {
    when(transactionService.create(transactionRequestDTO)).thenReturn(transactionResponseDTO);

    transactionResponseDTOSWithId.add(transactionResponseDTO);
    when(transactionService.findById(any())).thenReturn(transactionResponseDTOSWithId);

    transactionResponseDTOSWithoutId.add(transactionResponseDTO);
    when(transactionService.findAll()).thenReturn(transactionResponseDTOSWithoutId);
  }

  @Test
  @DisplayName("TODO")
  void createTransactionTest() {
    TransactionResponseDTO result = transactionController.createTransaction(transactionRequestDTO);

    assertThat(result).isEqualTo(transactionResponseDTO);
  }

  @Test
  @DisplayName("TODO")
  void retrieveTransactionWithIdTest() {
    List<TransactionResponseDTO> result = transactionController.retrieveTransaction(1L);

    //TODO assert sorting
    assertThat(result).isEqualTo(transactionResponseDTOSWithId);
  }

  @Test
  @DisplayName("TODO")
  void retrieveTransactionWithoutIdTest() {
    List<TransactionResponseDTO> result = transactionController.retrieveTransaction(null);

    //TODO assert sorting
    assertThat(result).isEqualTo(transactionResponseDTOSWithoutId);
  }
}