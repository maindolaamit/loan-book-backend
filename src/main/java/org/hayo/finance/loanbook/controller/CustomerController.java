package org.hayo.finance.loanbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hayo.finance.loanbook.dto.LoanApplication;
import org.hayo.finance.loanbook.dto.request.NewLoanApplicationRequest;
import org.hayo.finance.loanbook.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("v1/customer/{customer-id}/")
@Tag(name = "Customer APIs", description = "LoanBook Customer APIs/Endpoints")
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/loan/application/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Submit Loan Application Request", description = "API to submit a loan application request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LoanApplication> submitLoanApplication(@Valid
                                                        @PathVariable("customer-id") @NotNull String customerId,
                                                        @NotNull @RequestBody NewLoanApplicationRequest request) {
        log.info("Received loan request for user: {}", customerId);
        LoanApplication application = service.submitNewLoanApplication(customerId, request);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/loan/application/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Loan Applications", description = "API to get all loan applications for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LoanApplication>> getAllLoanApplicationsForUser(@Valid
                                                                               @PathVariable("customer-id")
                                                                               @NotBlank @NotNull String customerId
    ) {
        var allApplications = service.getAllLoanApplicationsForUser(customerId);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @GetMapping("/loan/application/active")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Active/Pending/Approved Loan Applications", description = "API to get all active loan applications for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LoanApplication>> getAllActiveLoanApplicationsForUser(@Valid
                                                                                     @PathVariable("customer-id")
                                                                                     @NotBlank @NotNull String customerId
    ) {
        var allApplications = service.getAllActiveLoanApplicationsForUser(customerId);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @GetMapping("/loan/application/inactive")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Inactive/Paid/Rejected Loan Applications", description = "API to get all inactive loan applications for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LoanApplication>> getAllInactiveLoanApplicationsForUser(@Valid
                                                                                       @PathVariable("customer-id")
                                                                                       @NotBlank @NotNull String customerId
    ) {
        var allApplications = service.getInactiveLoanApplicationsForUser(customerId);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @PutMapping("/loan/{loan-id}/repay/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Repay Loan Amount", description = "API to repay a loan amount.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LoanApplication> repayLoanAmount(@Valid
                                                           @PathVariable("customer-id") @NotNull String customerId,
                                                           @PathVariable("loan-id") @NotNull String loanId,
                                                           @RequestParam("amount") @NotNull Double amount
    ) {
        log.info("Received loan request for user: {}", customerId);
        LoanApplication loanRequestId = service.repayLoanAmount(customerId, loanId, amount);
        return new ResponseEntity<>(loanRequestId, HttpStatus.CREATED);
    }
}
