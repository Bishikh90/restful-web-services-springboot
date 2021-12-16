package com.mech2dude.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

//Controller
@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    // GET
    // What will be URI
    // method which will define the URI
//    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World Spring Boot";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello-world-SpringBoot");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello-world, %s",name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    // @RequestHeader(name = "Accept-Language", required = false)Locale locale
    public String helloWorldInternationalized(){
        return messageSource.getMessage("good.morning.message", null,
                "Default Message", LocaleContextHolder.getLocale());
    }
}
