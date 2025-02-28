package com.urban.oasis.urban.oasis_ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String createUserControllerHandling(){
        return "Welcome to Home Controller Handling Class";
    }
}
