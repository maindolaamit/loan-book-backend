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
import org.hayo.finance.loanbook.dto.request.LoanApplicationRejectRequest;
import org.hayo.finance.loanbook.dto.response.BasicApiResponse;
import org.hayo.finance.loanbook.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/admin/{admin-id}/")
@Tag(name = "Admin Controller", description = "Admin APIs")
public class AdminController {

    private final AdminService service;

    @GetMapping("/loan/application/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Loan Applications", description = "API to get all loan applications for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications(@Valid
                                                                        @PathVariable("admin-id")
                                                                        @NotBlank @NotNull String userId
    ) {
        log.info("Getting all loan applications from service for admin: {}", userId);
        var allApplications = service.getAllLoanApplications(userId);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @GetMapping("/loan/application/all-pending")
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
        log.info("Getting all pending loan applications from service for admin: {}", userId);
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
        log.info("Getting loan application from service for admin: {} and applicationId: {}", userId, applicationId);
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
    public ResponseEntity<BasicApiResponse> approveLoanApplicationsForId(@Valid
                                                                         @PathVariable("admin-id")
                                                                         String userId,
                                                                         @PathVariable("application-id")
                                                                         @NotBlank @NotNull String applicationId
    ) {
        log.info("Approving loan application from service for admin: {} and applicationId: {}", userId, applicationId);
        service.approveLoanApplicationsForId(userId, applicationId);
        BasicApiResponse message = BasicApiResponse.builder()
                .message("Loan Application Approved").timestamp(LocalDateTime.now()).build();
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
    public ResponseEntity<BasicApiResponse> rejectLoanApplicationsForId(@Valid
                                                                        @PathVariable("admin-id")
                                                                        @NotBlank @NotNull
                                                                        String userId,
                                                                        @PathVariable("application-id")
                                                                        @NotBlank @NotNull
                                                                        String applicationId,
                                                                        @NotNull @RequestBody
                                                                        LoanApplicationRejectRequest reason
    ) {
        log.info("Rejecting loan application from service for admin: {} and applicationId: {}", userId, applicationId);
        service.rejectLoanApplicationsForId(userId, applicationId, reason.rejectionReason());
        BasicApiResponse message = BasicApiResponse.builder().message("Loan Application Rejected successfully")
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
