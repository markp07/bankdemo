package xyz.markpost.bankdemo.restassured;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
class TransactionsRA {

  private final String CONTEXT_PATH = "/api/v1/transactions";

  @LocalServerPort
  private int port;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  private Client client;
  private Account account;
  private Account contraAccount;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;

    cleanDatabase();

    client = new Client();
    client.setFirstName("Foo");
    client.setLastName("Bar");
    client = clientRepository.save(client);

    account = new Account();
    account.setClient(client);
    account.setNumber("Test123456");
    account.setType(AccountType.CHECKING);
    account.setBalance(0.0f);
    account = accountRepository.save(account);

    contraAccount = new Account();
    contraAccount.setClient(client);
    contraAccount.setNumber("Test123456");
    contraAccount.setType(AccountType.CHECKING);
    contraAccount.setBalance(0.0f);
    contraAccount = accountRepository.save(contraAccount);
  }

  @Test
  void createTransactionUsingAccountIdContraAccountIdTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    Map<String, Object> resultTransaction = jsonPath.getMap("");

    int resultId = (int) resultTransaction.get("id");
    int accountId = (int) resultTransaction.get("accountId");
    String accountName = (String) resultTransaction.get("accountName");
    int contraAccountId = (int) resultTransaction.get("contraAccountId");
    String contraAccountName = (String) resultTransaction.get("contraAccountName");
    String type = (String) resultTransaction.get("type");
    String resultDate = (String) resultTransaction.get("date");
    float amount = (float) resultTransaction.get("amount");
    String description = (String) resultTransaction.get("description");

    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(account.getId(), accountId),
        () -> assertEquals(account.getClient().getFullName(), accountName),
        () -> assertEquals(contraAccount.getId(), contraAccountId),
        () -> assertEquals(contraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(TransactionType.DEPOSIT.toString(), type),
        () -> assertEquals("2019-01-01", resultDate),
        () -> assertEquals(1.5f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void createTransactionUsingAccountIdContraAccountNumberTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountNumber", contraAccount.getNumber());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    Map<String, Object> resultTransaction = jsonPath.getMap("");

    int resultId = (int) resultTransaction.get("id");
    int accountId = (int) resultTransaction.get("accountId");
    String accountName = (String) resultTransaction.get("accountName");
    int contraAccountId = (int) resultTransaction.get("contraAccountId");
    String contraAccountName = (String) resultTransaction.get("contraAccountName");
    String type = (String) resultTransaction.get("type");
    String resultDate = (String) resultTransaction.get("date");
    float amount = (float) resultTransaction.get("amount");
    String description = (String) resultTransaction.get("description");

    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(account.getId(), accountId),
        () -> assertEquals(account.getClient().getFullName(), accountName),
        () -> assertEquals(contraAccount.getId(), contraAccountId),
        () -> assertEquals(contraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(TransactionType.DEPOSIT.toString(), type),
        () -> assertEquals("2019-01-01", resultDate),
        () -> assertEquals(1.5f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void createTransactionUsingAccountNumberContraAccountIdTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountNumber", account.getNumber());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    Map<String, Object> resultTransaction = jsonPath.getMap("");

    int resultId = (int) resultTransaction.get("id");
    int accountId = (int) resultTransaction.get("accountId");
    String accountName = (String) resultTransaction.get("accountName");
    int contraAccountId = (int) resultTransaction.get("contraAccountId");
    String contraAccountName = (String) resultTransaction.get("contraAccountName");
    String type = (String) resultTransaction.get("type");
    String resultDate = (String) resultTransaction.get("date");
    float amount = (float) resultTransaction.get("amount");
    String description = (String) resultTransaction.get("description");

    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(account.getId(), accountId),
        () -> assertEquals(account.getClient().getFullName(), accountName),
        () -> assertEquals(contraAccount.getId(), contraAccountId),
        () -> assertEquals(contraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(TransactionType.DEPOSIT.toString(), type),
        () -> assertEquals("2019-01-01", resultDate),
        () -> assertEquals(1.5f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void createTransactionUsingAccountNumberContraAccountNumberTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountNumber", account.getNumber());
    transaction.put("contraAccountNumber", contraAccount.getNumber());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    Map<String, Object> resultTransaction = jsonPath.getMap("");

    int resultId = (int) resultTransaction.get("id");
    int accountId = (int) resultTransaction.get("accountId");
    String accountName = (String) resultTransaction.get("accountName");
    int contraAccountId = (int) resultTransaction.get("contraAccountId");
    String contraAccountName = (String) resultTransaction.get("contraAccountName");
    String type = (String) resultTransaction.get("type");
    String resultDate = (String) resultTransaction.get("date");
    float amount = (float) resultTransaction.get("amount");
    String description = (String) resultTransaction.get("description");

    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(account.getId(), accountId),
        () -> assertEquals(account.getClient().getFullName(), accountName),
        () -> assertEquals(contraAccount.getId(), contraAccountId),
        () -> assertEquals(contraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(TransactionType.DEPOSIT.toString(), type),
        () -> assertEquals("2019-01-01", resultDate),
        () -> assertEquals(1.5f, amount),
        () -> assertEquals("Test description", description)
    );
  }

  @Test
  void createTransactionUsingAccountIdNoContraAccountTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("No contra-account given.", message);
  }

  @Test
  void createTransactionUsingAccountNumberNoContraAccountTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountNumber", account.getNumber());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("No contra-account given.", message);
  }

  @Test
  void createTransactionUsingContraAccountIdNoAccountTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("No account given.", message);
  }

  @Test
  void createTransactionUsingContraAccountNumberNoAccountTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("contraAccountNumber", contraAccount.getNumber());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("No account given.", message);
  }

  @Test
  void createTransactionUsingAccountIdContraAccountIdNotExistingTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", 0);
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Contra-account with ID 0 not found.", message);
  }

  @Test
  void createTransactionUsingAccountIdContraAccountNumberNotExistingTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getNumber());
    transaction.put("contraAccountNumber", "DOESNTEXIST");
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Contra-account with number DOESNTEXIST not found.", message);
  }

  @Test
  void createTransactionUsingAccountIdNotExistingContraAccountIdTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", 0);
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account with ID 0 not found.", message);
  }

  @Test
  void createTransactionUsingAccountNumberNotExistingContraAccountIdTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountNumber", "DOESNTEXIST");
    transaction.put("contraAccountNumber", contraAccount.getNumber());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Account with number DOESNTEXIST not found.", message);
  }



  @Test
  void createTransactionNoTypeGivenTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Type not given.", message);
  }

  @Test
  void createTransactionNoDateGivenTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("amount", "1.50");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Date not given.", message);
  }

  @Test
  void createTransactionNoDescriptionGivenTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("amount", "1.50");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();


    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Description not given.", message);
  }

  @Test
  void createTransactionNoAmountGivenTest() {
    Map<String, Object> transaction = new HashMap<>();
    transaction.put("accountId", account.getId());
    transaction.put("contraAccountId", contraAccount.getId());
    transaction.put("type", TransactionType.DEPOSIT.toString());
    transaction.put("description", "Test description");
    transaction.put("date", "2019-01-01");

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(transaction)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Amount not given.", message);
  }

  @Test
  void retrieveTransaction() throws ParseException {
    String dateString = "2019-01-01";
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    Date date = new java.sql.Date(utilDate.getTime());

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setContraAccount(contraAccount);
    transaction.setType(TransactionType.DEPOSIT);
    transaction.setDate(date);
    transaction.setAmount(1.5f);
    transaction.setDescription("Test transaction");
    transaction = transactionRepository.save(transaction);

    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/" + transaction.getId())
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

    Transaction finalTransaction = transaction;
    assertAll(
        "Check return json after creating account.",
        () -> assertNotNull(resultId),
        () -> assertEquals(account.getId(), accountId),
        () -> assertEquals(account.getClient().getFullName(), accountName),
        () -> assertEquals(contraAccount.getId(), contraAccountId),
        () -> assertEquals(contraAccount.getClient().getFullName(), contraAccountName),
        () -> assertEquals(TransactionType.DEPOSIT.toString(), type),
        () -> assertEquals(dateString, resultDate),
        () -> assertEquals(finalTransaction.getAmount(), amount),
        () -> assertEquals(finalTransaction.getDescription(), description)
    );
  }

  @Test
  void retrieveTransactionNotExisting() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/0")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType("application/json")
        .extract()
        .response();

    JsonPath jsonPath = response.jsonPath();
    String message = jsonPath.getString("message");

    assertEquals("Transaction with ID 0 not found.", message);
  }

  @Test
  void retrieveTransactionNoId() {
    Response response = RestAssured.given()
        .when()
        .get(CONTEXT_PATH + "/")
        .then()
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
        .contentType("application/json")
        .extract()
        .response();
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
