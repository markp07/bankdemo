package xyz.markpost.bankdemo.restassured;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.BankDemoApplication;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.AccountType;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.model.Transaction;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.ClientRepository;
import xyz.markpost.bankdemo.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = BankDemoApplication.class)
class AccountsRA {

  private final String CONTEXT_PATH = "/api/v1/accounts";

  @LocalServerPort
  private int port;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  private Client client;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;

    client = new Client();
    client.setFirstName("Foo");
    client.setLastName("Bar");
    client = clientRepository.save(client);
  }

  @Test
  void createAccountSuccessTest() {
    Map<String, Object> account = new HashMap<>();
    account.put("clientId", client.getId());
    account.put("type", AccountType.CHECKING.toString());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String resultId = jsonPath.getString("id");
    String name = jsonPath.getString("name");
    String number = jsonPath.getString("number");
    String clientId = jsonPath.getString("clientId");
    String type = jsonPath.getString("type");

    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertNotNull(name),
        () -> assertNotNull(number),
        () -> assertEquals(Long.toString(client.getId()), clientId),
        () -> assertEquals(AccountType.CHECKING.toString(), type)
    );
  }

  @Test
  void createAccountNoBodyTest() {
    Map<String, Object> account = new HashMap<>();

    assertThrows(AssertionError.class, () -> RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response());
  }

  @Test
  void createAccountEmptyBodyTest() {
    Map<String, Object> account = new HashMap<>();

    assertThrows(AssertionError.class, () -> RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response());
  }

  @Test
  void createAccountNoClientIdTest() {
    Map<String, Object> account = new HashMap<>();
    account.put("type", AccountType.CHECKING.toString());

    assertThrows(AssertionError.class, () -> RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response());
  }

  @Test
  void createAccountNoTypeTest() {
    Map<String, Object> account = new HashMap<>();
    account.put("clientId", client.getId());

    assertThrows(AssertionError.class, () -> RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response());
  }

  @Test
  void retrieveAccount() {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + Long.toString(account.getId()))
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultAccounts = jsonPath.getList("");
    Map<String, Object> resultAccount = (Map<String, Object>) resultAccounts.get(0);
    int resultId = (int) resultAccount.get("id");
    String name = (String) resultAccount.get("name");
    String number = (String) resultAccount.get("number");
    int clientId = (int) resultAccount.get("clientId");
    String type = (String) resultAccount.get("type");

    Account finalAccount = account;
    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(client.getFullName(), name),
        () -> assertEquals(finalAccount.getNumber(), number),
        () -> assertEquals(Long.toString(client.getId()), Integer.toString(clientId)),
        () -> assertEquals(finalAccount.getType().toString(), type)
    );
  }

  @Test
  void retrieveAccountNoId() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/")
        .then()
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
        .contentType("application/json")
        .extract()
        .response();
  }

  @Test
  void retrieveNonExistingAccount() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/1")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultAccounts = jsonPath.getList("");
    assertTrue(resultAccounts.isEmpty());
  }

  @Test
  void retrieveTransactionsOfAccount() {
    //TODO: get transactions of client
  }

  //TODO: get empty transactions

  @Test
  void retrieveTransactionsOfNonExistingAccount() {
    assertThrows(AssertionError.class, () -> RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/1/transactions")
        .then()
        .statusCode(HttpStatus.OK.value()));
  }


  //TODO: update account
  //TODO: update account no fields given
  //TODO: update account not existing

  @Test
  void deleteAccount() {
    Account account = new Account();
    account = accountRepository.save(account);

    RestAssured.given()
        .when()
        .delete(CONTEXT_PATH + "/" + account.getId())
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  void deleteNonExistingAccount() {
    assertThrows(AssertionError.class, () -> RestAssured.given()
        .when()
        .delete(CONTEXT_PATH + "/0")
        .then()
        .statusCode(HttpStatus.OK.value()));
  }

  @AfterEach
  void cleanDatabase() {
    Iterable<Account> accounts = accountRepository.findAll();
    accounts.forEach((account -> accountRepository.delete(account)));

    Iterable<Client> clients = clientRepository.findAll();
    clients.forEach((client -> clientRepository.delete(client)));

    Iterable<Transaction> transactions = transactionRepository.findAll();
    transactions.forEach((transaction -> transactionRepository.delete(transaction)));
  }


}
