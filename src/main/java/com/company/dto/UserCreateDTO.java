package com.company.dto;

import lombok.*;
import com.company.validator.PasswordMatches;
import com.company.validator.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches(message = "Repeat password don't match")
public class UserCreateDTO {
    @UniqueUsername(message = "There is already user with this username!")
    @NotBlank(message = "Username can not be null")
    private String username;

    @NotBlank(message = "FirstName can not be null")
    private String firstName;

    @NotBlank(message = "LastName can not be null")
    private String lastName;

    @NotBlank(message = "Email can not be null")
    private String email;

    @NotBlank(message = "Address can not be null")
    private String address;

    @NotBlank(message = "PhoneNumber can not be null")
    private String phoneNumber;

    @NotBlank(message = "City can not be null")
    private String city;

    @NotBlank(message = "PostalCode can not be null")
    private int postalCode;

    @NotBlank(message = "Password can not be null")
    private String password;

    @NotBlank(message = "Repeat password can not be null")
    private String repeatPassword;




}
