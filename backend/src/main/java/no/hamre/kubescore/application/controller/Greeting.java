package no.hamre.kubescore.application.controller;

public class Greeting {
    private String name;
    private String greeting;

    public Greeting(String name, String greeting) {
        this.name = name;
        this.greeting = greeting;
    }

    public String getName() {
        return name;
    }

    public String getGreeting() {
        return greeting;
    }
}
