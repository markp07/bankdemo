package xyz.markpost.bankdemo.restassured;


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
import org.springframework.boot.web.server.LocalServerPort;
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

    cleanDatabase();

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

    assertEquals("Account details missing in request.", message);
  }

  @Test
  void createAccountEmptyBodyTest() {
    Map<String, Object> account = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account details missing in request.", message);
  }

  @Test
  void createAccountNoClientIdTest() {
    Map<String, Object> account = new HashMap<>();
    account.put("type", AccountType.CHECKING.toString());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account details missing in request.", message);
  }

  @Test
  void createAccountNoTypeTest() {
    Map<String, Object> account = new HashMap<>();
    account.put("clientId", client.getId());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(account)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account type missing in request.", message);
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
  void retrieveTransactionsOfAccount() throws ParseException {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Account account2 = new Account();
    account2.setClient(client);
    account2.setBalance(0);
    account2.setType(AccountType.CHECKING);
    account2.setNumber("TEST123457");
    account2 = accountRepository.save(account2);

    String dateString = "2019-01-01";
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    Date date = new java.sql.Date(utilDate.getTime());

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setContraAccount(account2);
    transaction.setType(TransactionType.DEPOSIT);
    transaction.setDate(date);
    transaction.setAmount(1.0f);
    transaction.setDescription("Test description");
    transaction = transactionRepository.save(transaction);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + account.getId() + "/transactions")
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
    Account finalAccount1 = account2;
    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(finalAccount.getId(), accountId),
        () -> assertEquals(finalAccount.getClient().getFullName(), accountName),
        () -> assertEquals(finalAccount1.getId(), contraAccountId),
        () -> assertEquals(finalAccount1.getClient().getFullName(), contraAccountName),
        () -> assertEquals(finalTransaction.getType().toString(), type),
        () -> assertEquals(dateString, resultDate),
        () -> assertEquals(1.0f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void retrieveEmptyTransactionsOfAccount() {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + account.getId() + "/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> resultTransactions = jsonPath.getList("");
    assertTrue(resultTransactions.isEmpty());
  }

  @Test
  void retrieveTransactionsOfNonExistingAccount() {
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

    assertEquals("Account with id 01not found.", message);
  }

  @Test
  void updateAccountSuccessTest() {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Client clientNew = new Client();
    clientNew.setFirstName("Foo");
    clientNew.setLastName("Bar");
    clientNew = clientRepository.save(client);

    Map<String, Object> accountUpdate = new HashMap<>();
    accountUpdate.put("clientId", clientNew.getId());
    accountUpdate.put("type", AccountType.SAVING.toString());

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(accountUpdate)
        .when()
        .patch(CONTEXT_PATH + "/" + account.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String resultId = jsonPath.getString("id");
    String name = jsonPath.getString("name");
    String number = jsonPath.getString("number");
    String clientId = jsonPath.getString("clientId");
    String type = jsonPath.getString("type");

    Client finalClientNew = clientNew;
    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertNotNull(name),
        () -> assertNotNull(number),
        () -> assertEquals(Long.toString(finalClientNew.getId()), clientId),
        () -> assertEquals(AccountType.SAVING.toString(), type)
    );
  }

  @Test
  void updateAccountNoUpdatedFieldsSuccessTest() {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Map<String, Object> accountUpdate = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(accountUpdate)
        .when()
        .patch(CONTEXT_PATH + "/" + account.getId())
        .then()
        .statusCode(HttpStatus.OK.value())
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
  void updateNotExistingAccount() {
    Map<String, Object> accountUpdate = new HashMap<>();

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(accountUpdate)
        .when()
        .patch(CONTEXT_PATH + "/0")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account with id 0 not found.", message);
  }

  @Test
  void updateAccountNotExistingClient() {
    Account account = new Account();
    account.setClient(client);
    account.setBalance(0);
    account.setType(AccountType.CHECKING);
    account.setNumber("TEST123456");
    account = accountRepository.save(account);

    Map<String, Object> accountUpdate = new HashMap<>();
    accountUpdate.put("clientId", 0);

    Account finalAccount = account;
    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(accountUpdate)
        .when()
        .patch(CONTEXT_PATH + "/" + finalAccount.getId())
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

    assertEquals("Account with id 0 not found.", message);
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
