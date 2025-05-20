package com.davi.Quitanda.fruta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class FruitGetResponseDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 40, message = "O nome deve ter entre 2 e 40 caracteres")
    private String name;

    @JsonProperty(value = "price")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal price;

    @JsonProperty(value = "quantity")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantity;

}
