package ru.karod.tsm.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.models.enums.UserStatus;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "first_name")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "Email should be not empty")
    @ValidEmail(message = "Invalid Email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private Short age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;


    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn (name = "subject_id")
    )
    private List<Subject> subjects;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
