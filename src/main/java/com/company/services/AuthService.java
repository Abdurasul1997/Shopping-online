package com.company.services;

import com.company.domains.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.company.configs.security.UserDetails;
import com.company.dto.UserCreateDTO;
import com.company.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new UserDetails(authUser);
    }

    public void generate(UserCreateDTO dto) {

        AuthUser authUser = AuthUser
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .city(dto.getCity())
                .phoneNumber(dto.getPhoneNumber())
                .postalCode(dto.getPostalCode())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .repeatPassword(passwordEncoder.encode(dto.getRepeatPassword()))
                .active(true)
                .build();
        authRepository.save(authUser);
    }
}
