package com.maki.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/")
@RestController

public class BaseController {

    // Da todos los adicionales, pero podemos pedir solo los
    // que tengan un categoryId o un additionalId
    @GetMapping("health")
    public ResponseEntity<String> getAllAdicionales() {
       return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
