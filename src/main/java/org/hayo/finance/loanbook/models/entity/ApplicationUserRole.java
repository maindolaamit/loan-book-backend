package org.hayo.finance.loanbook.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApplicationUserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(unique = true)
    private String authority;

    @Column(name = "created_by")
    private String createdBy = "admin";
    @Column(name = "updated_by")
    private String updatedBy = "admin";
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    private List<ApplicationUser> users;
}
