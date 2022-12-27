package ru.karod.tsm.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;
import ru.karod.tsm.models.enums.Role;
import ru.karod.tsm.models.enums.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
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
    @ValidPassword(message = "Please enter the password according to the requirements: " +
            "from 6 to 20 characters, at least one digit or Latin letter is required")
    private String password;
    @Column(name = "age")
    private short age;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "verifiedStatus")
    private boolean verifiedStatus;

    @Column(name = "verificationCode")
    private short verificationCode;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "createdDate")
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
