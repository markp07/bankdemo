package xyz.markpost.bankdemo.restassured;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.BankDemoApplication;
import xyz.markpost.bankdemo.model.Account;
import xyz.markpost.bankdemo.model.AccountType;
import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.model.Transaction;
import xyz.markpost.bankdemo.model.TransactionType;
import xyz.markpost.bankdemo.repository.AccountRepository;
import xyz.markpost.bankdemo.repository.ClientRepository;
import xyz.markpost.bankdemo.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = BankDemoApplication.class)
class ClientsRA {

  private final String CONTEXT_PATH = "/api/v1/clients";

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

    cleanDatabase();

    client = new Client();
    client.setFirstName("Foo");
    client.setLastName("Bar");
    client.setBirthDate(new Date(0));
    client.setAddress("Test Address 1");
  }

  @Test
  void createClientSuccessTest() {
    Map<String, Object> clientData = new HashMap<>();
    clientData.put("firstName", this.client.getFirstName());
    clientData.put("lastName", this.client.getLastName());
    clientData.put("birthDate", this.client.getBirthDate());
    clientData.put("address", this.client.getAddress());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String resultId = jsonPath.getString("id");
    String firstName = jsonPath.getString("firstName");
    String lastName = jsonPath.getString("lastName");
    String birthDate = jsonPath.getString("birthDate");
    String address = jsonPath.getString("address");

    assertAll(
        "Check return json after creating client.",
        () -> assertNotNull(resultId),
        () -> assertEquals(client.getFirstName(), firstName),
        () -> assertEquals(client.getLastName(), lastName),
        () -> assertEquals(client.getBirthDate().toString(), birthDate),
        () -> assertEquals(client.getAddress(), address),
        () -> assertNotNull(lastName)
    );
  }

  @Test
  void createClientNoBodyTest() {
    Response response = RestAssured.given()
        .contentType("application/json")
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void createClientEmptyBodyTest() {
    Map<String, Object> clientData = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void createClientNoFirstNameTest() {
    Map<String, Object> clientData = new HashMap<>();
    clientData.put("lastName", this.client.getLastName());
    clientData.put("birthDate", this.client.getBirthDate());
    clientData.put("address", this.client.getAddress());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void createClientNoLastNameTest() {
    Map<String, Object> clientData = new HashMap<>();
    clientData.put("firstName", this.client.getFirstName());
    clientData.put("birthDate", this.client.getBirthDate());
    clientData.put("address", this.client.getAddress());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void createClientNoBirthDateTest() {
    Map<String, Object> clientData = new HashMap<>();
    clientData.put("firstName", this.client.getFirstName());
    clientData.put("lastName", this.client.getLastName());
    clientData.put("address", this.client.getAddress());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void createClientNoAddressTest() {
    Map<String, Object> clientData = new HashMap<>();
    clientData.put("firstName", this.client.getFirstName());
    clientData.put("lastName", this.client.getLastName());
    clientData.put("birthDate", this.client.getBirthDate());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientData)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client details missing in request.", message);
  }

  @Test
  void retrieveClient() {
    Client clientLocal = clientRepository.save(client);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + clientLocal.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultAccounts = jsonPath.getList("");
    Map<String, Object> resultClient = (Map<String, Object>) resultAccounts.get(0);
    int resultId = (int) resultClient.get("id");
    String firstName = (String) resultClient.get("firstName");
    String lastName = (String) resultClient.get("lastName");
    String birthDate = (String) resultClient.get("birthDate");
    String address = (String) resultClient.get("address");

    assertAll(
        "Check return json after creating client.",
        () -> assertThat(resultId).isGreaterThan(0),
        () -> assertEquals(client.getFirstName(), firstName),
        () -> assertEquals(client.getLastName(), lastName),
        () -> assertEquals(client.getBirthDate().toString(), birthDate),
        () -> assertEquals(client.getAddress(), address)
    );
  }

  @Test
  void retrieveNonExistingClient() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/1")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultClients = jsonPath.getList("");
    assertTrue(resultClients.isEmpty());
  }

  @Test
  void retrieveClientNoId() {
    RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/")
        .then()
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
        .contentType("application/json")
        .extract()
        .response();
  }

  @Test
  void retrieveAccountsOfClient() throws ParseException {
    Client localClient = clientRepository.save(client);

    Account account = new Account();
    account.setClient(localClient);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + localClient.getId() + "/accounts")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultAccounts = jsonPath.getList("");
    assertEquals(1, resultAccounts.size());

    Map<String, Object> resultAccount = (Map<String, Object>) resultAccounts.get(0);

    int resultId = (int) resultAccount.get("id");
    int clientId = (int) resultAccount.get("clientId");
    String accountName = (String) resultAccount.get("name");
    String type = (String) resultAccount.get("type");
    String number = (String) resultAccount.get("number");

    Account finalAccount = account;
    assertAll(
        "Check return json after creating account.",
        () -> assertThat(resultId).isGreaterThan(0),
        () -> assertEquals(localClient.getId(), clientId),
        () -> assertEquals(finalAccount.getClient().getFullName(), accountName),
        () -> assertEquals(finalAccount.getType().toString(), type),
        () -> assertEquals(finalAccount.getNumber(), number)
    );
  }

  @Test
  void retrieveEmptyAccountsOfClient() {
    Client localClient = clientRepository.save(client);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + localClient.getId() + "/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultAccounts = jsonPath.getList("");
    assertThat(resultAccounts).isEmpty();
  }

  @Test
  void retrieveAccountsOfNonExistingClient() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/1/transactions")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client with id 1 not found.", message);
  }


  @Test
  void retrieveTransactionsOfClient() throws ParseException {
    Client localClient = clientRepository.save(client);

    Account account = new Account();
    account.setClient(localClient);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Client clientOther = new Client();
    clientOther.setFirstName("Bar");
    clientOther.setLastName("Foo");
    clientOther = clientRepository.save(clientOther);

    Account contraAccount = new Account();
    contraAccount.setClient(clientOther);
    contraAccount.setBalance(0);
    contraAccount.setType(AccountType.CHECKING);
    contraAccount.setNumber("TEST123457");
    contraAccount = accountRepository.save(contraAccount);

    String dateString = "2019-01-01";
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    Date date = new java.sql.Date(utilDate.getTime());

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setContraAccount(contraAccount);
    transaction.setType(TransactionType.DEPOSIT);
    transaction.setDate(date);
    transaction.setAmount(1.0f);
    transaction.setDescription("Test description");
    transaction = transactionRepository.save(transaction);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + localClient.getId() + "/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultTransactions = jsonPath.getList("");
    assertEquals(1, resultTransactions.size());

    Map<String, Object> resultTransaction = (Map<String, Object>) resultTransactions.get(0);

    int resultId = (int) resultTransaction.get("id");
    int accountId = (int) resultTransaction.get("accountId");
    String accountName = (String) resultTransaction.get("accountName");
    int contraAccountId = (int) resultTransaction.get("contraAccountId");
    String contraAccountName = (String) resultTransaction.get("contraAccountName");
    String type = (String) resultTransaction.get("type");
    String resultDate = (String) resultTransaction.get("date");
    float amount = (float) resultTransaction.get("amount");
    String description = (String) resultTransaction.get("description");

    Account finalAccount = account;
    Transaction finalTransaction = transaction;
    Account finalContraAccount = contraAccount;
    assertAll(
        "Check return json after retrieving transactions of client.",
        () -> assertThat(resultId).isGreaterThan(0),
        () -> assertEquals(finalAccount.getId(), accountId),
        () -> assertEquals(finalAccount.getClient().getFullName(), accountName),
        () -> assertEquals(finalContraAccount.getId(), contraAccountId),
        () -> assertEquals(finalContraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(finalTransaction.getType().toString(), type),
        () -> assertEquals(dateString, resultDate),
        () -> assertEquals(1.0f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void retrieveEmptyTransactionsOfClient() {
    Client localClient = clientRepository.save(client);

    Account account = new Account();
    account.setClient(localClient);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + localClient.getId() + "/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultTransactions = jsonPath.getList("");
    assertThat(resultTransactions).isEmpty();
  }

  @Test
  void retrieveTransactionsOfNonExistingClient() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/1/transactions")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client with id 1 not found.", message);
  }

  @Test
  void updateAccountSuccessTest() {
    Client clientLocal = clientRepository.save(client);

    Map<String, Object> clientUpdateData = new HashMap<>();
    clientUpdateData.put("address", "New address 1");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientUpdateData)
        .when()
        .patch(CONTEXT_PATH + "/" + clientLocal.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    long resultId = Long.parseLong(jsonPath.getString("id"));
    String firstName = jsonPath.getString("firstName");
    String lastName = jsonPath.getString("lastName");
    String birthDate = jsonPath.getString("birthDate");
    String address = jsonPath.getString("address");

    assertAll(
        "Check return json after updating client.",
        () -> assertEquals(clientLocal.getId(), resultId),
        () -> assertEquals(clientLocal.getFirstName(), firstName),
        () -> assertEquals(clientLocal.getLastName(), lastName),
        () -> assertEquals(clientLocal.getBirthDate().toString(), birthDate),
        () -> assertEquals("New address 1", address),
        () -> assertNotNull(lastName)
    );
  }

  @Test
  void updateClientNoUpdatedFieldsSuccessTest() {
    Client clientLocal = clientRepository.save(client);

    Map<String, Object> clientUpdateData = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientUpdateData)
        .when()
        .patch(CONTEXT_PATH + "/" + clientLocal.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    long resultId = Long.parseLong(jsonPath.getString("id"));
    String firstName = jsonPath.getString("firstName");
    String lastName = jsonPath.getString("lastName");
    String birthDate = jsonPath.getString("birthDate");
    String address = jsonPath.getString("address");

    assertAll(
        "Check return json after updating client.",
        () -> assertEquals(clientLocal.getId(), resultId),
        () -> assertEquals(clientLocal.getFirstName(), firstName),
        () -> assertEquals(clientLocal.getLastName(), lastName),
        () -> assertEquals(clientLocal.getBirthDate().toString(), birthDate),
        () -> assertEquals(clientLocal.getAddress(), address),
        () -> assertNotNull(lastName)
    );
  }

  @Test
  void updateNotExistingClient() {
    Map<String, Object> clientUpdate = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(clientUpdate)
        .when()
        .patch(CONTEXT_PATH + "/0")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client with id 0 not found.", message);
  }

  @Test
  void deleteClient() {
    Client client = new Client();
    client = clientRepository.save(client);

    RestAssured.given()
        .when()
        .delete(CONTEXT_PATH + "/" + client.getId())
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  void deleteNonExistingClient() {
    Response response = RestAssured.given()
        .when()
        .patch(CONTEXT_PATH + "/0")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Client with id 0 not found.", message);
  }

  private void cleanDatabase() {
    Iterable<Transaction> transactions = transactionRepository.findAll();
    transactions.forEach((transaction -> transactionRepository.delete(transaction)));

    Iterable<Account> accounts = accountRepository.findAll();
    accounts.forEach((account -> accountRepository.delete(account)));

    Iterable<Client> clients = clientRepository.findAll();
    clients.forEach((client -> clientRepository.delete(client)));
  }

}
