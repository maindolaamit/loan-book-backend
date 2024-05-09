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
import org.hayo.finance.loanbook.dto.LoanApplicationRejectRequest;
import org.hayo.finance.loanbook.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/{admin-id}/")
@Tag(name = "Admin Controller", description = "Admin APIs")
public class AdminController {

    private final AdminService service;

    @GetMapping("/loan/application/pending")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Active Loan Applications", description = "API to get all active loan applications for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LoanApplication>> getAllPendingLoanApplications(@Valid
                                                                               @PathVariable("admin-id")
                                                                               @NotBlank @NotNull String userId
    ) {
        log.info("Getting all pending loan applications for admin: {}", userId);
        var allApplications = service.getAllPendingLoanApplications(userId);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @GetMapping("/loan/application/{application-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Loan Application", description = "API to get a loan application for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LoanApplication> getLoanApplicationsForId(@Valid
                                                                    @PathVariable("admin-id")
                                                                    String userId,
                                                                    @PathVariable("application-id")
                                                                    @NotBlank @NotNull String applicationId
    ) {
        var application = service.getLoanApplicationsForId(userId, applicationId);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }


    @PutMapping("/loan/application/{application-id}/approve")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Approve Loan Application", description = "API to approve a loan application for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> approveLoanApplicationsForId(@Valid
                                                               @PathVariable("admin-id")
                                                               String userId,
                                                               @PathVariable("application-id")
                                                               @NotBlank @NotNull String applicationId
    ) {
        service.approveLoanApplicationsForId(userId, applicationId);
        String message = "Loan Application Approved";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @PutMapping("/loan/application/{application-id}/reject")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Reject Loan Application", description = "API to reject a loan application for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> rejectLoanApplicationsForId(@Valid
                                                              @PathVariable("admin-id")
                                                              @NotBlank @NotNull
                                                              String userId,
                                                              @PathVariable("application-id")
                                                              @NotBlank @NotNull
                                                              String applicationId,
                                                              @NotNull @RequestBody
                                                              LoanApplicationRejectRequest reason
    ) {
        service.rejectLoanApplicationsForId(userId, applicationId, reason.rejectionReason());
        String message = "Loan Application Rejected successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
