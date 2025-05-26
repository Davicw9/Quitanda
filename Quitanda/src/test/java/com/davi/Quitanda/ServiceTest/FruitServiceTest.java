package com.davi.Quitanda.ServiceTest;

import com.davi.Quitanda.fruta.entity.Fruit;
import com.davi.Quitanda.fruta.exception.EntityNotFoundException;
import com.davi.Quitanda.fruta.repository.FruitIRepository;
import com.davi.Quitanda.fruta.service.FruitService;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author davia
 * @date 21/05/2025
 */
// Diz ao JUnit para usar a extensão do Mockito
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@ActiveProfiles("h2")
class FruitServiceTest {

    // Cria um mock do repositório que será "fingido" no teste
    @Mock
    private FruitIRepository fruitIRepository;

    // Injeta o mock acima dentro do objeto que estamos testando
    @InjectMocks
    private FruitService fruitService;

    // Um objeto de fruta usado nos testes
    private Fruit fruit;

    // Executado antes de cada teste: cria uma fruta de exemplo
    @BeforeEach
    void setUp() {
        fruit = new Fruit();
        fruit.setId(1L);
        fruit.setName("Banana");
    }

    // Teste que garante que uma fruta será salva quando não existe ainda
    @Test
    void shouldSaveFruitWhenNotExists() {
        // Simula que o nome "Banana" não existe no banco
        when(fruitIRepository.findByName("Banana")).thenReturn(Optional.empty());

        // Simula que ao salvar, ele devolve a fruta salva
        when(fruitIRepository.save(fruit)).thenReturn(fruit);

        // Executa o método que será testado
        Fruit saved = fruitService.save(fruit);

        // Verifica se o retorno não é nulo
        assertNotNull(saved);
        // Verifica se o nome retornado é o esperado
        assertEquals("Banana", saved.getName());
        // Verifica se o método save foi chamado 1x
        verify(fruitIRepository).save(fruit);
    }

    // Teste que verifica se será lançada exceção ao tentar salvar fruta já existente
    @Test
    void shouldThrowWhenSavingExistingFruit() {
        // Simula que já existe uma fruta com o mesmo nome
        when(fruitIRepository.findByName("Banana")).thenReturn(Optional.of(fruit));

        // Verifica se a exceção EntityExistsException será lançada
        assertThrows(EntityExistsException.class, () -> fruitService.save(fruit));
    }

    // Testa se deletar uma fruta existente funciona corretamente
    @Test
    void shouldDeleteExistingFruit() {
        // Simula que encontrou a fruta com id 1
        when(fruitIRepository.findById(1L)).thenReturn(Optional.of(fruit));

        // Executa a exclusão
        fruitService.delete(1L);

        // Verifica se o método deleteById foi chamado
        verify(fruitIRepository).deleteById(1L);
    }

    // Testa se lançar exceção ao tentar deletar fruta inexistente
    @Test
    void shouldThrowWhenDeletingNonExistingFruit() {
        // Simula que nenhuma fruta foi encontrada
        when(fruitIRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se será lançada a exceção correta
        assertThrows(EntityNotFoundException.class, () -> fruitService.delete(1L));
    }

    // Testa se a atualização da fruta funciona corretamente
    @Test
    void shouldUpdateFruitSuccessfully() {
        // Simula que encontrou a fruta para atualizar
        when(fruitIRepository.findById(1L)).thenReturn(Optional.of(fruit));
        // Simula que não tem nenhuma outra fruta com o mesmo nome
        when(fruitIRepository.findByName("Banana")).thenReturn(Optional.of(fruit));
        // Simula o retorno ao salvar
        when(fruitIRepository.save(fruit)).thenReturn(fruit);

        // Executa o update
        Fruit updated = fruitService.update(fruit);

        // Verifica se o nome é o esperado
        assertEquals("Banana", updated.getName());
        // Verifica se o método save foi chamado
        verify(fruitIRepository).save(fruit);
    }

    // Testa se lançar exceção ao atualizar para um nome já existente em outra fruta
    @Test
    void shouldThrowWhenUpdatingToExistingNameOfAnotherFruit() {
        // Cria outra fruta com o mesmo nome
        Fruit another = new Fruit();
        another.setId(2L);
        another.setName("Banana");

        // Simula que encontrou a fruta original pelo ID
        when(fruitIRepository.findById(1L)).thenReturn(Optional.of(fruit));
        // Simula que encontrou outra fruta com o mesmo nome
        when(fruitIRepository.findByName("Banana")).thenReturn(Optional.of(another));

        // Verifica se será lançada a exceção de nome duplicado
        assertThrows(EntityExistsException.class, () -> fruitService.update(fruit));
    }

    // Testa se retorna uma página de frutas ordenadas por nome
    @Test
    void shouldFindAllFruitsOrderedByName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        List<Fruit> fruits = List.of(fruit);
        Page<Fruit> page = new PageImpl<>(fruits, pageable, fruits.size());

        // Simula o retorno da consulta paginada
        when(fruitIRepository.findAll(pageable)).thenReturn(page);

        // Executa o método
        Page<Fruit> result = fruitService.findAll(0, 10);

        // Verifica se retornou 1 fruta
        assertEquals(1, result.getContent().size());
        // Verifica se o nome da fruta é o esperado
        assertEquals("Banana", result.getContent().get(0).getName());
    }

    // Testa se lança exceção ao passar parâmetros inválidos de paginação
    @Test
    void shouldThrowWhenInvalidPageOrSize() {
        assertThrows(IllegalArgumentException.class, () -> fruitService.findAll(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> fruitService.findAll(0, 0));
    }

    // Anotação do JUnit que indica que este é um método de teste
    @Test
    void shouldReturnTrueIfFruitListIsNotEmpty() {
        // Criamos uma lista contendo um único objeto fruit (pode ser o mock ou um objeto de teste)
        List<Fruit> fruits = List.of(fruit);

        // Verificamos se a lista NÃO está vazia, ou seja, contém pelo menos uma fruta
        // O assertTrue espera uma condição booleana verdadeira
        assertTrue(!fruits.isEmpty(), "A lista de frutas não deveria estar vazia");
    }

}
