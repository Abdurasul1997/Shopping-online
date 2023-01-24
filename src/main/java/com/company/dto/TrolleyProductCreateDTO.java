package com.company.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrolleyProductCreateDTO {
   private String size;
   private  Integer theNumber;
}
