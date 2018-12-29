package no.hamre.kubescore.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/v1/hello")
    public Greeting greeting() {
        return new Greeting("Bjørn Hamre", "Hello ");
    }
}
