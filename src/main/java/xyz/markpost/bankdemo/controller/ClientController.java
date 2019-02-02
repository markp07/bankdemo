package xyz.markpost.bankdemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.bankdemo.model.AccountResponseDTO;
import xyz.markpost.bankdemo.model.ClientRequestDTO;
import xyz.markpost.bankdemo.model.ClientResponseDTO;
import xyz.markpost.bankdemo.service.AccountService;
import xyz.markpost.bankdemo.service.ClientService;


@RestController
@RequestMapping("v1/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @Autowired
  private AccountService accountService;

  /**
   *
   * @param clientRequestDTO
   * @return
   */
  @PostMapping(produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.create(clientRequestDTO);
  }

  /**
   *
   * @param id
   * @return
   */
  @GetMapping(path = "{id}", produces = "application/json")
  public List<ClientResponseDTO> retrieveClient(
      @PathVariable(value = "id", required = false) Long id) {
    if (null != id) {
      return clientService.findById(id);
    } else {
      return clientService.findAll();
    }
  }

  /**
   *
   * @param id
   * @return
   */
  @GetMapping(path = "{id}/accounts", produces = "application/json")
  public List<AccountResponseDTO> retrieveClientAccounts(@PathVariable(value = "id") Long id) {
    return accountService.findByClientId(id);
  }

  /**
   *
   * @param id
   * @param clientRequestDTO
   * @return
   */
  @PatchMapping(path = "{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ClientResponseDTO updateClient(@PathVariable("id") Long id,
      @RequestBody ClientRequestDTO clientRequestDTO) {
    return clientService.update(id, clientRequestDTO);
  }

  /**
   *
   * @param id
   */
  @DeleteMapping(path = "{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClient(@PathVariable("id") Long id) {
    clientService.delete(id);
  }

}
