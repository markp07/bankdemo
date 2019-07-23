package xyz.markpost.bankdemo.service;

import java.util.List;
import xyz.markpost.bankdemo.dto.AccountRequestDTO;
import xyz.markpost.bankdemo.dto.AccountResponseDTO;

/**
 *
 */
public interface AccountService {

  /**
   *
   * @param accountRequestDTO
   * @return
   */
  AccountResponseDTO create(AccountRequestDTO accountRequestDTO);

  /**
   *
   * @param id
   * @return
   */
  List<AccountResponseDTO> findById(Long id);

  /**
   *
   * @param clientId
   * @return
   */
  List<AccountResponseDTO> findByClientId(Long clientId);

  /**
   *
   * @return
   */
  List<AccountResponseDTO> findAll();

  /**
   *
   * @param id
   * @param accountRequestDTO
   * @return
   */
  AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO);

  /**
   *
   * @param id
   */
  void delete(Long id);
}
