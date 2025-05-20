package com.davi.Quitanda.fruta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author davia
 * @date 20/05/2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FruitPostResponseDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "quantity")
    private Integer quantity;
}
