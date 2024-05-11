package org.hayo.finance.loanbook.controller;

import io.restassured.RestAssured;
import org.hayo.finance.loanbook.it.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:latest:///test",
        "spring.datasource.username=test",
        "spring.datasource.password=test"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api/v1";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Get all loan applications")
    public void testGetAllLoanApplications() {
        given()
                .when()
                .get("/admin/1/loan/application/all")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Get a loan application by id")
    public void testGetLoanApplication() {
        given()
                .when()
                .get("/admin/1/loan/application/1")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Get a loan application by invalid id")
    public void testLoanApplicationByInvalidId() {
        given()
                .when()
                .get("/admin/1/loan/application/100")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Get a loan application by invalid id - null value")
    public void testLoanApplicationByNullId() {
        given()
                .when()
                .get("/admin/1/loan/application/")
                .then()
                .statusCode(500)
        ;
    }


    @Test
    @DisplayName("Get a loan application by invalid id")
    public void testApproveLoanApplicationByInvalidId() {
        // approve loan request
        given()
                .when()
                .put("/admin/1/loan/application/100/approve")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    @DisplayName("Get a loan application by invalid id - null value")
    public void testApproveLoanApplicationById() {
        // approve loan request
        given()
                .when()
                .put("/admin/1/loan/application/1/approve")
                .then()
                .statusCode(200)
        ;

        // error when approving already approved loan request
        given()
                .when()
                .put("/admin/1/loan/application/1/approve")
                .then()
                .statusCode(404)
        ;
    }
}
