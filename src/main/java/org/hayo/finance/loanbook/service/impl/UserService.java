package org.hayo.finance.loanbook.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.UserLoginRequest;
import org.hayo.finance.loanbook.dto.request.UserRegistrationRequest;
import org.hayo.finance.loanbook.models.entity.ApplicationUser;
import org.hayo.finance.loanbook.models.entity.ApplicationUserRole;
import org.hayo.finance.loanbook.repository.ApplicationRoleRepository;
import org.hayo.finance.loanbook.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final ApplicationUserRepository userRepository;
    private final ApplicationRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Checking if user exists: {}", username);
        val user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Loading user by username: {}", username);
        return user;
    }

    public long getUserId(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getUserId();
    }

    public boolean createCustomer(UserRegistrationRequest request) {
        // check if user is already registered
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });

        saveUserIfNotExists(request, Set.of("ROLE_CUSTOMER"));
        return true;
    }

    private String getTrimmedUsername(String username) {
        return username.trim().toLowerCase().replace(" ", "");
    }

    public String saveUserIfNotExists(UserRegistrationRequest request, Set<String> roles) {
        // check if user already exists
        val byEmail = userRepository.findByEmail(request.email());
        if (byEmail.isEmpty()) {
            log.info("Creating user: {}", request.email());
            StringBuilder username = new StringBuilder(getTrimmedUsername(request.firstName()));
            if (request.lastName() != null && !request.lastName().isBlank())
                username.append(getTrimmedUsername(request.lastName()));

            userRepository.save(ApplicationUser.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .username(username.toString())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .createdAt(LocalDateTime.now()).createdBy("system")
                    .updatedAt(LocalDateTime.now()).updatedBy("system")
                    .roles(roles.stream().map(this::getRoleFor).collect(Collectors.toSet()))
                    .build());
            log.info("User created: {}", username);
            return username.toString();
        }

        log.info("User already exists: {}", request.email());
        return byEmail.get().getUsername();
    }

    private ApplicationUserRole getRoleFor(@NotNull String authority) {
        if (roleRepository.findByAuthority(authority).isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            return ApplicationUserRole.builder().authority(authority)
                    .createdBy("system").updatedBy("system")
                    .createdAt(now).updatedAt(now)
                    .build();
        } else {
            return roleRepository.findByAuthority(authority).get();
        }
    }

    public boolean saveRoleIfNotExists(String authority) {
        if (roleRepository.findByAuthority(authority).isEmpty()) {
            roleRepository.save(getRoleFor(authority));
            return true;
        }
        return false;
    }

    public boolean verifyRegistration(String token) {
        return true;
    }

    public String login(@NotNull UserLoginRequest request) {
        val byEmail = userRepository.findByEmail(request.email());
        // check if user exists
        if (byEmail.isPresent()) {
            // check if password matches
            if (passwordEncoder.matches(request.password(), byEmail.get().getPassword())) {
                // generate token
                return jwtService.generateToken(byEmail.get().getUsername());
            }
        }

        throw new IllegalArgumentException("Invalid user credentials");
    }
}
