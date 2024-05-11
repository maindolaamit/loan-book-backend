package org.hayo.finance.loanbook.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.it.AbstractIntegrationTest;
import org.hayo.finance.loanbook.models.enums.ApprovalStatus;
import org.hayo.finance.loanbook.models.enums.PaymentStatus;
import org.hayo.finance.loanbook.models.exceptions.InvalidLoanStatusException;
import org.hayo.finance.loanbook.models.exceptions.InvalidValueException;
import org.hayo.finance.loanbook.models.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Slf4j
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:latest:///test",
        "spring.datasource.username=test",
        "spring.datasource.password=test"
})
public class IntegrationServiceTest extends AbstractIntegrationTest {

    @Autowired
    LoanService loanService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("Test connection")
    void testConnection() {
        assertThat(postgreSQLContainer.isCreated());
        assertThat(postgreSQLContainer.isRunning());
    }
    @Test
    @DisplayName("Create a new loan application")
    public void testCreateNewLoanApplication() {
        String userId = "100";
        var loanApplication = NewLoanApplicationRequest.builder().amount(1000.0).terms(3).build();
        assertNotNull(loanApplication);
        val submitted = customerService.submitNewLoanApplication(userId, loanApplication);
        assertNotNull(submitted);
        assertEquals(1000.0, submitted.loanAmount());
        assertEquals(3, submitted.numOfTerms());
        assertEquals(userId, submitted.customerId());
        assertEquals(ApprovalStatus.PENDING.name(), submitted.status());
        assertEquals(0.0, submitted.amountPaid());
        assertEquals(PaymentStatus.PENDING.name(), submitted.paymentStatus());

        // verify that the loan application was created
        var loanApplications = customerService.getAllLoanApplicationsForUser(userId);
        assertNotNull(loanApplications);
        assertEquals(1, loanApplications.size());
    }

    @Test
    @DisplayName("Get all loan applications")
    public void testGetAllLoanApplications() {
        var loanApplicationsBefore = adminService.getAllLoanApplications("amit");
        String userId = "100";
        var loanApplication = NewLoanApplicationRequest.builder().amount(1000.0).terms(3).build();
        assertNotNull(loanApplication);
        loanService.newLoanApplication(userId, loanApplication);
        loanService.newLoanApplication(userId, loanApplication);
        loanService.newLoanApplication(userId, loanApplication);

        var loanApplicationsAfter = adminService.getAllLoanApplications("amit");
        assertNotNull(loanApplicationsAfter);
        assertEquals(3, loanApplicationsAfter.size() - loanApplicationsBefore.size());
    }

    @Test
    @DisplayName("Approve a loan application - throws exception if already approved")
    public void testApproveLoanApplicationThrowsException() {
        String userId = "100";
        // Invalid application id
        assertThrows(RecordNotFoundException.class, () -> adminService.approveLoanApplicationsForId("amit", "100"));

        // Valid application id
        var loanApplicationRequest = NewLoanApplicationRequest.builder().amount(1000.0).terms(3).build();
        assertNotNull(loanApplicationRequest);
        val submitted = loanService.newLoanApplication(userId, loanApplicationRequest);
        assertNotNull(submitted);
        adminService.approveLoanApplicationsForId("amit", submitted.applicationId());


        // Approve the loan application
        val application = adminService.getLoanApplicationsForId("amit", submitted.applicationId());
        assertNotNull(application);
        assertEquals("APPROVED", application.status());

        // throws error if already approved
        assertThrows(InvalidLoanStatusException.class, () -> adminService.approveLoanApplicationsForId("amit", application.applicationId()));
    }

    @Test
    @DisplayName("Reject a loan application")
    public void testRejectLoanApplicationThrowsException() {
        String userId = "100";
        // Invalid application id
        assertThrows(RecordNotFoundException.class, () -> adminService.approveLoanApplicationsForId("amit", "100"));

        // Valid application id
        var loanApplicationRequest = NewLoanApplicationRequest.builder().amount(1000.0).terms(3).build();
        assertNotNull(loanApplicationRequest);
        val submitted = loanService.newLoanApplication(userId, loanApplicationRequest);
        assertNotNull(submitted);
        adminService.rejectLoanApplicationsForId("amit", submitted.applicationId(), "rejected");

        // Verify that the loan application was rejected
        val application = adminService.getLoanApplicationsForId("amit", submitted.applicationId());
        assertNotNull(application);
        assertEquals("REJECTED", application.status());
        assertEquals("rejected", application.rejectionReason());

        // throws error if already rejected
        assertThrows(InvalidLoanStatusException.class, () -> adminService.approveLoanApplicationsForId("amit", application.applicationId()));
    }

    @Test
    @DisplayName("Customer repays loan amount")
    public void testRepayLoanAmount() {
        String userId = "100";
        var loanApplicationRequest = NewLoanApplicationRequest.builder().amount(1000.0).terms(3).build();
        assertNotNull(loanApplicationRequest);
        val submitted = loanService.newLoanApplication(userId, loanApplicationRequest);
        assertNotNull(submitted);

        // Repay the loan amount with invalid amount
        assertThrows(InvalidValueException.class, () ->
                customerService.repayLoanAmount(userId, submitted.applicationId(), 0.0)
        );

        // Repay the loan amount which is not approved
        assertThrows(InvalidLoanStatusException.class, () ->
                customerService.repayLoanAmount(userId, submitted.applicationId(), 100.0)
        );

        // Approve the loan application
        adminService.approveLoanApplicationsForId("amit", submitted.applicationId());
        val application = adminService.getLoanApplicationsForId("amit", submitted.applicationId());
        assertNotNull(application);
        assertEquals("APPROVED", application.status());

        // throws error if already paid
        assertThrows(InvalidValueException.class, () -> customerService.repayLoanAmount(userId, application.applicationId(), 4000.0));

        // Repay the loan amount
        val loanApplication = customerService.repayLoanAmount(userId, application.applicationId(), 1000.0);
        assertNotNull(loanApplication);
        assertEquals("PAID", loanApplication.paymentStatus());
        assertEquals(1000.0, loanApplication.amountPaid());

        // throws error if already paid
        assertThrows(InvalidLoanStatusException.class, () -> customerService.repayLoanAmount(userId, application.applicationId(), 1000.0));
    }
}
