package org.hayo.finance.loanbook.controller;

import io.restassured.RestAssured;
import lombok.val;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.response.ApiErrorSchema;
import org.hayo.finance.loanbook.it.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:latest:///test",
        "spring.datasource.username=test",
        "spring.datasource.password=test"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Check connection")
    void testConnection() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api/v1";
        RestAssured.port = port;
    }
    @Test
    @DisplayName("Create a new loan application - with empty body")
    void testCreateNewLoanError() throws IOException {
//        LoanApplicationRequest request = LoanApplicationRequest.builder()
//                .amount(1200.00)
//                .terms(12)
//                .build();
        val request = Files.readString(Paths.get("src/test/resources/new-loan-application.json"));

        val errorSchema = given()
                .when()
//                .body(request)
                .post("/customer/{customer-id}/loan/application", "1")
                .then()
                .statusCode(500)
                .extract()
                .as(ApiErrorSchema.class);

        assertEquals("500", errorSchema.getCode());
    }

    @Test
    @DisplayName("Create a new loan application")
    void testCreateNewLoanSuccess() throws IOException {
//        LoanApplicationRequest request = LoanApplicationRequest.builder()
//                .amount(1200.00)
//                .terms(12)
//                .build();
        val request = Files.readString(Paths.get("src/test/resources/new-loan-application.json"));

        val application = given()
                .when()
                .body(request)
                .post("/customer/{customer-id}/loan/application", "1")
                .then()
                .statusCode(201)
                .extract()
                .as(LoanApplication.class);

        assertEquals(12, application.numOfTerms());
        assertEquals(1200.00, application.loanAmount());
    }

    @Test
    @DisplayName("Get all loan applications")
    void testGetAllLoanApplications() {
        val loanApplications = given()
                .when()
                .get("/customer/{customer-id}/loan/application/all", "1")
                .then()
                .statusCode(200)
                .extract()
                .as(LoanApplication[].class);

        assertEquals(2, loanApplications.length);
    }

    @Test
    @DisplayName("Get all Pending loan applications")
    void testGetAllPendingLoanApplications() {
        val loanApplications = given()
                .when()
                .get("/customer/{customer-id}/loan/application/pending", "1")
                .then()
                .statusCode(200)
                .extract()
                .as(LoanApplication[].class);

        assertEquals(2, loanApplications.length);
    }

    @Test
    @DisplayName("Get all InActive/closed/rejected loan applications")
    void testGetAllInactiveLoanApplications() {
        val loanApplications = given()
                .when()
                .get("/customer/{customer-id}/loan/application/inactive", "1")
                .then()
                .statusCode(200)
                .extract()
                .as(LoanApplication[].class);

        assertEquals(0, loanApplications.length);
    }
}