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
import org.hayo.finance.loanbook.utils.RegistrationUtility;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;

    public void requestPasswordReset(String email) {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = RegistrationUtility.generateVerificationCode();
        user.setVerificationCode(token);
        user.setVerificationCodeExpiry(LocalDateTime.now().plusDays(1));
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("To reset your password, click the link below:\n" +
                "http://localhost:8080/reset-password?token=" + token);

        mailSender.send(mailMessage);
    }

    public void resetPassword(String email, String token, String newPassword) {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        // check the user token and expiry
        if (!token.equals(user.getVerificationCode()))
            throw new IllegalArgumentException("Invalid token or token expired");

        if (user.getVerificationCodeExpiry() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiry()))
            throw new IllegalArgumentException("Token expired");

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);
        userRepository.save(user);
    }

    public String regenerateToken(String email) {
        ApplicationUser userDetails = (ApplicationUser) loadUserByUsername(email);
        String token = jwtService.generateToken(userDetails.getEmail());
        userDetails.setToken(token);
        userRepository.save(userDetails);

        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Checking if user exists: {}", email);
        val user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Loading user by email: {}", email);
        return user;
    }

    public long getUserId(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getUserId();
    }

    public String createCustomer(UserRegistrationRequest request) {
        // check if user is already registered
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });

        String url = saveUserIfNotExists(request, Set.of("ROLE_CUSTOMER"));
        // send email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.email());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("To reset your password, click the link below:\n" + url);
        mailSender.send(mailMessage);

        return url;
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

            // get a token
            String token = RegistrationUtility.generateVerificationCode();
            LocalDateTime expiry = LocalDateTime.now().plusDays(1);

            ApplicationUser user = userRepository.save(ApplicationUser.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .username(username.toString())
                    .email(request.email())
                    .verificationCode(token).verificationCodeExpiry(expiry)
                    .password(passwordEncoder.encode(request.password()))
                    .createdAt(LocalDateTime.now()).createdBy("system")
                    .updatedAt(LocalDateTime.now()).updatedBy("system")
                    .roles(roles.stream().map(this::getRoleFor).collect(Collectors.toSet()))
                    .build());
            log.info("User created: {}", username);
            return RegistrationUtility.getVerificationUrl(request.email(), token);
        }

        log.info("User already exists: {}", request.email());
        throw new IllegalArgumentException("User already exists");
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        return jwtService.generateToken(request.email());
    }
}
