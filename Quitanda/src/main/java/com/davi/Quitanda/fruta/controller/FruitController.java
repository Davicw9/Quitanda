package com.davi.Quitanda.fruta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author davia
 * @date 20/05/2025
 */
@RestController
@RequestMapping(path = "/frutas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FruitController {
}
