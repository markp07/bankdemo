package xyz.markpost.bankdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.dto.AccountRequestDTO;
import xyz.markpost.bankdemo.dto.AccountResponseDTO;
import xyz.markpost.bankdemo.model.AccountType;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.ClientRepository;


@Service
@Transactional
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ClientRepository clientRepository;

  private static final long BANK_NUMBER_MIN = 10000000;
  private static final long BANK_NUMBER_MAX = 99999999;

  /**
   *
   * @param accountRequestDTO
   * @return
   */
  public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {
    Optional<Client> clientOptional = clientRepository.findById(accountRequestDTO.getClientId());

    if (clientOptional.isPresent()) {
      Client client = clientOptional.get();
      Account account = new Account();

      account.setClient(client);
      account.setNumber(createAccountNumber());
      account.setType(accountRequestDTO.getType());
      account.setBalance(0);

      account = accountRepository.save(account);

      return createResponseAccount(account);
    } else {
      throw new EntityNotFoundException(
          "Client with id " + accountRequestDTO.getClientId().toString() + " not found.");
    }
  }

  /**
   *
   * @param id
   * @return
   */
  public List<AccountResponseDTO> findById(Long id) {
    Account account = findSingleAccount(id);
    ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    if (null != account) {
      AccountResponseDTO accountResponseDTO = createResponseAccount(account);
      accountResponseDTOS.add(accountResponseDTO);
    }

    return accountResponseDTOS;
  }

  /**
   *
   * @param clientId
   * @return
   */
  public List<AccountResponseDTO> findByClientId(Long clientId) {
    Client client = findSingleClient(clientId);

    if (null != client) {
      List<Account> accounts = client.getAccounts();

      ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

      accounts.forEach(account -> {
        AccountResponseDTO accountResponseDTO = createResponseAccount(account);
        accountResponseDTOS.add(accountResponseDTO);
      });

      return accountResponseDTOS;
    } else {
      throw new EntityNotFoundException("Client with id " + clientId.toString() + " not found.");
    }
  }

  /**
   *
   * @return
   */
  public List<AccountResponseDTO> findAll() {
    Iterable<Account> accounts = accountRepository.findAll();
    ArrayList<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

    accounts.forEach(account -> {
      AccountResponseDTO accountResponseDTO = createResponseAccount(account);
      accountResponseDTOS.add(accountResponseDTO);
    });

    return accountResponseDTOS;
  }

  /**
   *
   * @param id
   * @param accountRequestDTO
   * @return
   */
  public AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO) {
    Account account = findSingleAccount(id);

    if (null != account) {
      Long clientId = accountRequestDTO.getClientId();
      if (null != clientId) {
        Client client = findSingleClient(clientId);

        if (null != client) {
          account.setClient(client);
        } else {
          throw new EntityNotFoundException("Client with id " + id.toString() + " not found.");
        }
      }

      AccountType accountType = accountRequestDTO.getType();
      if (null != accountType) {
        account.setType(accountType);
      }

      account = accountRepository.save(account);
    } else {
      throw new EntityNotFoundException("Account with id " + id.toString() + " not found.");
    }

    return createResponseAccount(account);
  }

  /**
   *
   * @param id
   */
  public void delete(Long id) {
    Account account = findSingleAccount(id);

    if (null != account) {
      accountRepository.delete(account);
    } else {
      throw new EntityNotFoundException("Client with id " + id.toString() + " not found.");
    }
  }

  /**
   *
   * @param id
   * @return
   */
  private Account findSingleAccount(Long id) {
    Optional<Account> accountOptional = accountRepository.findById(id);

    return accountOptional.orElse(null);
  }

  /**
   *
   * @param id
   * @return
   */
  private Client findSingleClient(Long id) {
    Optional<Client> clientOptional = clientRepository.findById(id);

    return clientOptional.orElse(null);
  }

  /**
   *
   * @param account
   * @return
   */
  private AccountResponseDTO createResponseAccount(Account account) {
    Client client = account.getClient();
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

    accountResponseDTO.setId(account.getId());
    accountResponseDTO.setClientId(client.getId());
    accountResponseDTO.setName(client);
    accountResponseDTO.setNumber(account.getNumber());
    accountResponseDTO.setType(account.getType());

    return accountResponseDTO;
  }

  /**
   *
   * @return
   */
  private String createAccountNumber() {
    long number = ThreadLocalRandom.current().nextLong(BANK_NUMBER_MIN, BANK_NUMBER_MAX + 1);

    return "BANK" + Long.toString(number);
  }

}
