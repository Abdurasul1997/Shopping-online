package com.company.domains;

import lombok.*;
import com.company.enums.Currency;
import com.company.enums.Gender;
import com.company.enums.ProductType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String name;
    private String price;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @OneToOne
    private Uploads cover;
}

