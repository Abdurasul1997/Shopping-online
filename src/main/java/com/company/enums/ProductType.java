package com.company.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@AllArgsConstructor
@Getter
public enum ProductType {
    SHOES("Shoes") ,
    PANTS("Pants"),
    SHIRT("Shirt"),
    WATCH("Watch");


    private final String key;

    public static ProductType findByName(String genre) {
        return Arrays.stream(ProductType.values())
                .filter(genre1 -> genre1.name().equalsIgnoreCase(genre))
                .findFirst()
                .orElse(ProductType.SHOES);
    }

    public String getKey() {
        return key;
    }
}
