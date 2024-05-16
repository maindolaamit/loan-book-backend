package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.models.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);
    Optional<ApplicationUser> findByEmail(String email);
}
