package com.davi.Quitanda.fruta.repository;

import com.davi.Quitanda.fruta.entity.Fruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author davia
 * @date 20/05/2025
 */
@Repository
public interface FruitIRepository extends JpaRepository<Fruit, Long> {

    /**
     * Retorna uma página de frutas com base nas informações de paginação fornecidas.
     *
     * @param pageable objeto Pageable com informações de paginação (número da página, tamanho, ordenação, etc.)
     * @return uma página de frutas (Page<Fruit>)
     */
    @Query("select f from Fruit f")
    Page<Fruit> findAllPageable(Pageable pageable);

    Optional <Fruit> findById(Long id);
    Optional <Fruit> findByName(String name);
}
