package xyz.markpost.bankdemo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.markpost.bankdemo.dto.AccountRequestDTO;
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.AccountType;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.ClientRepository;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountSuccess() {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setType(AccountType.SAVING);

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccountResponseDTO responseDTO = accountService.create(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getClientId());
        assertEquals(AccountType.SAVING, responseDTO.getType());
    }

    @Test
    void createAccountClientNotFound() {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setClientId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.create(requestDTO));
    }

    @Test
    void findByIdSuccess() {
        Client client = new Client();
        client.setId(1L);
        Account account = new Account();
        account.setId(1L);
        account.setClient(client);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        List<AccountResponseDTO> responseDTOs = accountService.findById(1L);

        assertFalse(responseDTOs.isEmpty());
        assertEquals(1L, responseDTOs.get(0).getId());
    }

    @Test
    void findByIdAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        List<AccountResponseDTO> responseDTOs = accountService.findById(1L);

        assertTrue(responseDTOs.isEmpty());
    }

    @Test
    void updateAccountSuccess() {
        Account account = new Account();
        account.setId(1L);

        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setType(AccountType.CHECKING);

        Client client = new Client();
        client.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccountResponseDTO responseDTO = accountService.update(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getClientId());
        assertEquals(AccountType.CHECKING, responseDTO.getType());
    }

    @Test
    void updateAccountNotFound() {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setClientId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.update(1L, requestDTO));
    }

    @Test
    void deleteAccountSuccess() {
        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> accountService.delete(1L));
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void deleteAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.delete(1L));
    }
}