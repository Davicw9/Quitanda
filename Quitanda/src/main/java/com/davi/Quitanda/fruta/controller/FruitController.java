package com.davi.Quitanda.fruta.controller;

import com.davi.Quitanda.fruta.dto.FruitGetResponseDto;
import com.davi.Quitanda.fruta.dto.FruitPostRequestDto;
import com.davi.Quitanda.fruta.entity.Fruit;
import com.davi.Quitanda.fruta.service.FruitService;
import com.davi.Quitanda.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author davia
 * @date 20/05/2025
 */
@RestController
@RequestMapping(path = "/frutas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FruitController {

    private final FruitService fruitService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save", consumes = "application/json")
    public ResponseEntity<FruitGetResponseDto> save(@RequestBody @Valid FruitPostRequestDto fruitRequestDto){
        Fruit fruit = objectMapperUtil.map(fruitRequestDto, Fruit.class);
        Fruit savedFruit = fruitService.save(fruit);

        FruitGetResponseDto fruitResponseDto = objectMapperUtil.map(savedFruit, FruitGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(fruitResponseDto);
    }

    @GetMapping(path = "findall", produces = "application/json")
    public ResponseEntity<Page<FruitGetResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Fruit> fruitPage = fruitService.findAll(page, size);
        Page<FruitGetResponseDto> fruitPageResponseDto = fruitPage.map(fruit -> objectMapperUtil.map(fruit, FruitGetResponseDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(fruitPageResponseDto);
    }
    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<FruitGetResponseDto> update(@PathVariable Long id, @RequestBody FruitPostRequestDto fruitRequestDto){
        Fruit fruit = objectMapperUtil.map(fruitRequestDto, Fruit.class);
        fruit.setId(id);
        Fruit updatedFruit = fruitService.update(fruit);

        FruitGetResponseDto fruitResponseDto = objectMapperUtil.map(updatedFruit, FruitGetResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(fruitResponseDto);
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        fruitService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
