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
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.dto.ClientRequestDTO;
import xyz.markpost.bankdemo.dto.ClientResponseDTO;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.ClientService;
import xyz.markpost.bankdemo.service.TransactionService;
import xyz.markpost.bankdemo.util.TransactionSortByDate;

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


  @Test
  @DisplayName("Test creating a client")
  void createClientTest() {
    ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    when(clientService.create(clientRequestDTO)).thenReturn(clientResponseDTO);

    ClientResponseDTO result = clientController.createClient(clientRequestDTO);

    assertThat(result).isEqualTo(clientResponseDTO);
  }

  @Test
  @DisplayName("Test retrieving a client")
  void retrieveClientWithIdTest() {
    List<ClientResponseDTO> clientResponseDTOS = new ArrayList<>();

    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTO);
    when(clientService.findById(any())).thenReturn(clientResponseDTOS);

    List<ClientResponseDTO> result = clientController.retrieveClient(1L);

    assertThat(result)
        .hasSize(1)
        .isEqualTo(clientResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieving all clients")
  void retrieveClientWithoutIdTest() {
    List<ClientResponseDTO> clientResponseDTOS = new ArrayList<>();

    ClientResponseDTO clientResponseDTOA = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOA);

    ClientResponseDTO clientResponseDTOB = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOB);

    ClientResponseDTO clientResponseDTOC = new ClientResponseDTO();
    clientResponseDTOS.add(clientResponseDTOC);

    when(clientService.findAll()).thenReturn(clientResponseDTOS);

    List<ClientResponseDTO> result = clientController.retrieveClient(null);

    assertThat(result)
        .hasSize(3)
        .isEqualTo(clientResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieving all accounts from client")
  void retrieveClientAccountsTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO accountResponseDTOA = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOA);

    AccountResponseDTO accountResponseDTOB = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOB);

    AccountResponseDTO accountResponseDTOC = new AccountResponseDTO();
    accountResponseDTOS.add(accountResponseDTOC);

    when(accountService.findByClientId(any())).thenReturn(accountResponseDTOS);

    List<AccountResponseDTO> result = clientController.retrieveClientAccounts(1L);

    assertThat(result)
        .hasSize(3)
        .isEqualTo(accountResponseDTOS);
  }

  @Test
  @DisplayName("Test retrieve all transactions from all accounts from client")
  void retrieveClientAccountsTransactionsTest() {
    List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    AccountResponseDTO accountResponseDTOA = new AccountResponseDTO();
    accountResponseDTOA.setId(1L);
    accountResponseDTOS.add(accountResponseDTOA);

    AccountResponseDTO accountResponseDTOB = new AccountResponseDTO();
    accountResponseDTOB.setId(2L);
    accountResponseDTOS.add(accountResponseDTOB);

    when(accountService.findByClientId(any())).thenReturn(accountResponseDTOS);

    List<TransactionResponseDTO> transactionResponseDTOSA = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTOA = new TransactionResponseDTO();
    transactionResponseDTOA.setDate(new Date(0));
    transactionResponseDTOSA.add(transactionResponseDTOA);

    TransactionResponseDTO transactionResponseDTOB = new TransactionResponseDTO();
    transactionResponseDTOB.setDate(new Date(172800000));
    transactionResponseDTOSA.add(transactionResponseDTOB);

    when(transactionService.findByAccountId(1L)).thenReturn(transactionResponseDTOSA);

    List<TransactionResponseDTO> transactionResponseDTOSB = new ArrayList<>();

    TransactionResponseDTO transactionResponseDTOC = new TransactionResponseDTO();
    transactionResponseDTOC.setDate(new Date(86400000));
    transactionResponseDTOSB.add(transactionResponseDTOC);

    TransactionResponseDTO transactionResponseDTOD = new TransactionResponseDTO();
    transactionResponseDTOD.setDate(new Date(345600000));
    transactionResponseDTOSB.add(transactionResponseDTOD);

    when(transactionService.findByAccountId(2L)).thenReturn(transactionResponseDTOSB);

    List<TransactionResponseDTO> result = clientController.retrieveClientAccountsTransactions(1L);

    assertThat(result)
        .hasSize(4)
        .isSortedAccordingTo(new TransactionSortByDate());
  }

  @Test
  @DisplayName("Test updating a client")
  void updateClientTest() {
    ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
    ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
    when(clientService.update(any(Long.class),any(ClientRequestDTO.class))).thenReturn(clientResponseDTO);

    ClientResponseDTO result = clientController.updateClient(1L, clientRequestDTO);

    assertThat(result).isEqualTo(clientResponseDTO);
  }

  @Test
  @DisplayName("Test deleting a client")
  void deleteClientTest() {
    clientController.deleteClient(1L);
    //We cannot assert anything here
  }

  }