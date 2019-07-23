package xyz.markpost.bankdemo.service;

import java.util.List;
import xyz.markpost.bankdemo.dto.ClientRequestDTO;
import xyz.markpost.bankdemo.dto.ClientResponseDTO;

public interface ClientService {

  /**
   *
   * @param clientRequestDTO
   * @return
   */
  ClientResponseDTO create(ClientRequestDTO clientRequestDTO);

  /**
   *
   * @param id
   * @return
   */
  List<ClientResponseDTO> findById(Long id);

  /**
   *
   * @return
   */
  List<ClientResponseDTO> findAll();

  /**
   *
   * @param id
   * @param clientRequestDTO
   * @return
   */
  ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO);

  /**
   *
   * @param id
   */
  void delete(Long id);

}
