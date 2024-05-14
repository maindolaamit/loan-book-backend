package org.hayo.finance.loanbook.repository;

import org.hayo.finance.loanbook.models.entity.ApplicationUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRoleRepository extends CrudRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findByAuthority(String authority);
}
