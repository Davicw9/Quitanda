package com.davi.Quitanda.fruta.service;

import com.davi.Quitanda.fruta.entity.Fruit;
import com.davi.Quitanda.fruta.exception.EntityNotFoundException;
import com.davi.Quitanda.fruta.repository.FruitIRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author davia
 * @date 20/05/2025
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class FruitService implements FruitIService{

    private final FruitIRepository fruitIRepository;

    /**
     * metodo para buscar um usuario através do id
     * ou lança uma exceção
     * @param id
     * @return uma entidade do tipo Fruit ou uma exceção
     */
    private Fruit getFruitOrThrow(Long id){
        return fruitIRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fruit " + id + " not found"));
    }

    @Override
    public Fruit save(Fruit fruit) {
        log.info("FruitService: save fruit {}", fruit.getName());
        Optional <Fruit> fruitOptionalName = fruitIRepository.findByName(fruit.getName());

        if(fruitOptionalName.isPresent()){
            throw new EntityExistsException("Fruit " + fruit.getName() + " already exists");
        }

        return fruitIRepository.save(fruit);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting fruit with id: {}", id);
        getFruitOrThrow(id);



        fruitIRepository.deleteById(id);
    }

    @Override
    public Fruit update(Fruit fruit) {
        log.info("updating fruit with id: {}", fruit.getId());
        getFruitOrThrow(fruit.getId());

        Optional<Fruit> fruitWithSameName = fruitIRepository.findByName(fruit.getName());
        if (fruitWithSameName.isPresent() && !fruitWithSameName.get().getId().equals(fruit.getId())) {
            throw new EntityExistsException("Fruit " + fruit.getName() + " already exists");
        }
        return fruitIRepository.save(fruit);
    }

    @Override
    public Page<Fruit> findAll(int page, int size) {
        log.info("Fetching all fruits from page {} of size {}", page, size);
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Página e tamanho devem ser valores positivos");
        }
        Pageable pageable = PageRequest.of(page, size);
        return fruitIRepository.findAll(PageRequest.of(page, size, Sort.by("name").ascending()));
    }
}
