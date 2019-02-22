package xyz.markpost.bankdemo.restassured;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.BankDemoApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes=BankDemoApplication.class)
public class ClientsRA {

  private final String CONTEXT_PATH = "/api/v1/clients";

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  public void testRA(){
    Map<String, String> client = new HashMap<>();
    String address = "TestAddress";
    client.put("address", address);
    String birthDate = "1900-01-01";
    client.put("birthDate", birthDate);
    String firstName = "TestName";
    client.put("firstName", firstName);
    String lastName = "NameTest";
    client.put("lastName", lastName);

    Response response = RestAssured.given()
        .contentType("application/json")
        .accept("application/json")
        .body(client)
        .when()
        .post(CONTEXT_PATH)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType("application/json")
        .extract()
        .response();

    String resultId = response.jsonPath().getString("id");
    assertNotNull(resultId);

    String resultAddress = response.jsonPath().getString("address");
    assertEquals(address, resultAddress);

    String resultBirthDate = response.jsonPath().getString("birthDate");
    assertEquals(birthDate, resultBirthDate);

    String resultFirstName = response.jsonPath().getString("firstName");
    assertEquals(resultFirstName, firstName);

    String resultLastName = response.jsonPath().getString("lastName");
    assertEquals(resultLastName, lastName);
  }

}
