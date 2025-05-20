package com.davi.Quitanda.fruta.entity;

import com.davi.Quitanda.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author davia
 * @date 20/05/2025
 */
@Entity
@Table(name = "frutas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Fruit extends PersistenceEntity implements Serializable {
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 40, message = "O nome deve ter entre 2 e 40 caracteres")
    @Column(name = "nome", nullable = false)
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(name = "quantidade", nullable = false)
    private Integer quantity;
}
