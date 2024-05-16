package org.hayo.finance.loanbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.UserLoginRequest;
import org.hayo.finance.loanbook.dto.request.UserRegistrationRequest;
import org.hayo.finance.loanbook.service.impl.JwtService;
import org.hayo.finance.loanbook.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "API to register a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> registerCustomer(@Valid
                                                   @NotNull @RequestBody UserRegistrationRequest request) {
        log.info("Received registration request for user: {}", request.firstName());
        val user = userService.createCustomer(request);
        if (user == null) {
            return new ResponseEntity<>("Error while creating the user!", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        }
    }


    @GetMapping("/verify-registration/{token}")
    @Operation(summary = "Verify user registration", description = "API to verify user registration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully verified",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> verifyRegistration(@PathVariable String token) {
        log.info("Received registration verification request for token: {}", token);
        val verified = userService.verifyRegistration(token);
        if (!verified) {
            return new ResponseEntity<>("Error while verifying the user!", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("User verified successfully!", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "API to login user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> login(@Valid @NotNull @RequestBody UserLoginRequest request) {
        log.info("Received login request for user: {}", request.email());
        val token = jwtService.login(request, userService);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
