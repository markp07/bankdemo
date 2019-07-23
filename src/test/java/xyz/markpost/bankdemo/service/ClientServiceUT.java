package xyz.markpost.bankdemo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.dto.ClientRequestDTO;
import xyz.markpost.bankdemo.dto.ClientResponseDTO;
import xyz.markpost.bankdemo.repository.ClientRepository;


@ExtendWith(SpringExtension.class)
class ClientServiceUT {

  @Mock
  ClientRepository clientRepository;

  @InjectMocks
  ClientService clientService;

  private ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
  private Client client = new Client();

  @BeforeEach
  void setUp() {
    client.setId(1);
    client.setFirstName("Foo");
    client.setLastName("Bar");
    client.setBirthDate(new Date(0));
    client.setAddress("Test Address 1");
  }

  @Test
  void createSuccessTest() {
    clientRequestDTO.setFirstName(client.getFirstName());
    clientRequestDTO.setLastName(client.getLastName());
    clientRequestDTO.setBirthDate(client.getBirthDate());
    clientRequestDTO.setAddress(client.getAddress());

    when(clientRepository.save(any(Client.class))).thenReturn(client);

    ClientResponseDTO result = clientService.create(clientRequestDTO);

    assertAll(
        () -> assertEquals(client.getId(), result.getId().longValue()),
        () -> assertEquals(client.getFirstName(), result.getFirstName()),
        () -> assertEquals(client.getLastName(), result.getLastName()),
        () -> assertEquals(client.getBirthDate(), result.getBirthDate()),
        () -> assertEquals(client.getAddress(), result.getAddress())
    );
  }

  @Test
  @Disabled
  void createEmptyRequestTest() {
    //TODO
  }

  @Test
  @Disabled
  void createNullRequestTest() {
    //TODO
  }

}