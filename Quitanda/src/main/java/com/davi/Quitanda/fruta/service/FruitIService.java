package com.davi.Quitanda.fruta.service;

import com.davi.Quitanda.fruta.entity.Fruit;
import org.springframework.data.domain.Page;

/**
 * @author davia
 * @date 20/05/2025
 */
public interface FruitIService {
    Fruit save(Fruit fruit);

    void delete(Long id);

    Fruit update(Fruit fruit);

    Page<Fruit> findAll(int page, int size);
}
