package com.jwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {

    @GetMapping("/welcome")
    public String welcome(){
        String s = "Hello from JWT";
        return s;
    }

    @GetMapping("/getuser")
    public String getUser(){
        return "{\"name\":\"Durgesh\"}";
    }

}
