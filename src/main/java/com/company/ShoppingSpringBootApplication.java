package com.company;


import com.company.domains.AuthRole;
import com.company.domains.AuthUser;
import com.company.repository.AuthRepository;
import com.company.repository.AuthRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class ShoppingSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingSpringBootApplication.class, args);
    }

    @Autowired
    AuthRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthRoleRepository authRoleRepository;


    @PostConstruct
    private void roleInit() {
        if (authRoleRepository.findAll().isEmpty()) {
            AuthRole role = new AuthRole();
            role.setName("ADMIN");
            role.setCode("ADMIN");
            AuthRole role1 = new AuthRole();
            role1.setCode("CUSTOMER");
            role1.setName("CUSTOMER");
            authRoleRepository.save(role);
            authRoleRepository.save(role1);
        }
    }

    @PostConstruct
    private void userInit() {


        List<AuthUser> user = userRepository.findAll();
        for (AuthUser authUser : user) {

            if (!authUser.getUsername().equals("admin") || user.size()==0) {
                AuthUser newUser = AuthUser.builder()
                        .firstName("ALI")
                        .lastName("VALI")
                        .phoneNumber("+998991234567")
                        .postalCode(10)
                        .active(true)
                        .username("admin")
                        .email("admin@localhost")
                        .address("Tashkent")
                        .city("Tashkent")
                        .password(passwordEncoder.encode("123"))
                        .repeatPassword(passwordEncoder.encode("123"))
                        .build();
                newUser.setRoles(authRoleRepository.findAll());
                userRepository.save(newUser);
            }


        }
    }
}
