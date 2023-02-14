package ru.karod.tsm.dto;

import lombok.Builder;
import lombok.Data;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDTO {
    private String id;
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;
    private short age;
    private String phoneNumber;

    /*public UserDTO(String firstName, String lastName, short age, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }*/
}
