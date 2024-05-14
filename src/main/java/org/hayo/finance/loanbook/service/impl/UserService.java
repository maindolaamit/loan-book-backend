package org.hayo.finance.loanbook.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.finance.loanbook.dto.request.UserRegistrationRequest;
import org.hayo.finance.loanbook.models.entity.ApplicationUser;
import org.hayo.finance.loanbook.models.entity.ApplicationUserRole;
import org.hayo.finance.loanbook.repository.ApplicationRoleRepository;
import org.hayo.finance.loanbook.repository.ApplicationUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Checking if user exists: {}", username);
        val user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Loading user by username: {}", username);
        return user;
    }

    public boolean createUser(UserRegistrationRequest request) {
        // check if user already exists
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        // create user
        log.info("Creating user: {}", request.username());
        userRepository.save(ApplicationUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(Set.of(ApplicationUserRole.builder().authority("ROLE_CUSTOMER").build()))
                .build());
        return true;
    }

    public boolean saveUserIfNotExists(String username, String email, String password,
                                       Set<String> roles) {
        if (userRepository.findByUsername(username).isEmpty()) {
            userRepository.save(ApplicationUser.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .createdAt(LocalDateTime.now()).createdBy("system")
                    .updatedAt(LocalDateTime.now()).updatedBy("system")
                    .roles(roles.stream().map(this::getRoleFor).collect(Collectors.toSet()))
                    .build());
            return true;
        }
        return false;
    }

    private ApplicationUserRole getRoleFor(String authority) {
        LocalDateTime now = LocalDateTime.now();
        return ApplicationUserRole.builder().authority(authority)
                .createdBy("system").updatedBy("system")
                .createdAt(now).updatedAt(now)
                .build();
    }

    public boolean saveRoleIfNotExists(String authority) {
        if (roleRepository.findByAuthority(authority).isEmpty()) {
            roleRepository.save(getRoleFor(authority));
            return true;
        }
        return false;
    }

    public Long getLoggedInCustomerId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((ApplicationUser) principal).getUserId();
        }

        throw new IllegalStateException("The current user is not authenticated.");
    }
}
