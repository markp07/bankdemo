package xyz.markpost.bankdemo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.dto.ClientRequestDTO;
import xyz.markpost.bankdemo.dto.ClientResponseDTO;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.repository.ClientRepository;

@ExtendWith(SpringExtension.class)
class ClientServiceTest {

  @Mock
  ClientRepository clientRepository;

  @InjectMocks
  ClientServiceImpl clientService;

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
  void createEmptyRequestTest() {
    ClientRequestDTO emptyRequestDTO = new ClientRequestDTO();

    assertThrows(NullPointerException.class, () -> clientService.create(emptyRequestDTO));
  }

  @Test
  void createNullRequestTest() {
    assertThrows(NullPointerException.class, () -> clientService.create(null));
  }

  @Test
  void findByIdSuccess() {
    when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

    List<ClientResponseDTO> result = clientService.findById(1L);

    assertFalse(result.isEmpty());
    assertEquals(1, result.get(0).getId());
  }

  @Test
  void findByIdNotFound() {
    when(clientRepository.findById(1L)).thenReturn(Optional.empty());

    List<ClientResponseDTO> result = clientService.findById(1L);

    assertTrue(result.isEmpty());
  }

  @Test
  void updateSuccess() {
    clientRequestDTO.setFirstName("Updated");
    when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
    when(clientRepository.save(any(Client.class))).thenReturn(client);

    ClientResponseDTO result = clientService.update(1L, clientRequestDTO);

    assertEquals("Updated", result.getFirstName());
  }

  @Test
  void updateNotFound() {
    when(clientRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> clientService.update(1L, clientRequestDTO));
  }

  @Test
  void deleteSuccess() {
    when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

    assertDoesNotThrow(() -> clientService.delete(1L));
    verify(clientRepository, times(1)).delete(client);
  }

  @Test
  void deleteNotFound() {
    when(clientRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> clientService.delete(1L));
  }
}