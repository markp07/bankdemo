package xyz.markpost.bankdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.markpost.bankdemo.service.ClientService;


@RestController
@RequestMapping("v1/clients")
public class TransactionController {

  @Autowired
  private ClientService clientService;

//  /**
//   *
//   * @param clientRequestDTO
//   * @return
//   */
//  @PostMapping(produces = "application/json")
//  @ResponseStatus(HttpStatus.CREATED)
//  public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
//    return clientService.create(clientRequestDTO);
//  }
//
//  /**
//   *
//   * @param id
//   * @return
//   */
//  @GetMapping(path = "id", produces = "application/json")
//  public List<ClientResponseDTO> retrieveClient(
//      @PathVariable(value = "id", required = false) Long id) {
//    if (null != id) {
//      return clientService.findById(id);
//    } else {
//      return clientService.findAll();
//    }
//  }
//
//  /**
//   *
//   * @param id
//   * @param client
//   * @return
//   */
//  @PatchMapping(path = "{id}", produces = "application/json")
//  @ResponseStatus(HttpStatus.OK)
//  public Client updateClient(@PathVariable("id") Long id, @RequestBody ClientRequestDTO client) {
//    return clientService.update(id, client);
//  }
//
//  /**
//   *
//   * @param id
//   */
//  @DeleteMapping(path = "{id}", produces = "application/json")
//  @ResponseStatus(HttpStatus.OK)
//  public void deleteClient(@PathVariable("id") Long id) {
//    clientService.delete(id);
//  }

}
