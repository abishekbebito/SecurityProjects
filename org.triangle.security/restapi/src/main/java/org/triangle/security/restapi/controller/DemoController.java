package org.triangle.security.restapi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {
    @GetMapping("/public/1")
    public String publicApi(){
        log.info("inside public");
        return "in public api";
    }
    @GetMapping("/private/1")
    public String privateApi(){
        return "in private api";
    }
}
