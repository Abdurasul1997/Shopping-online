package com.company.domains;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class AuthUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column( nullable = false , name = "first_name")
    private String firstName;

    @Column( nullable = false , name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false , name = "username")
    private String username;

    @Column(unique = true, nullable = false , name = "email")
    private String email;

    @Column(unique = true, nullable = false , name = "phone_number")
    private String phoneNumber;

    @Column( nullable = false , name = "address")
    private String address;

    @Column( nullable = false , name = "city")
    private String city;

    @Column(nullable = false , name = "password")
    private String password;

    @Column(nullable = false,name = "postal_code")
    private int postalCode;

    @Column(nullable = false ,name = "repeat_password")
    private String repeatPassword;

    @Column( columnDefinition = "bool default true" ,name = "active")
    private boolean active;

    @ManyToMany(targetEntity = AuthRole.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<AuthRole> roles = new ArrayList<>();

}
