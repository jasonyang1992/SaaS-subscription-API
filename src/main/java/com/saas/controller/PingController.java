package com.saas.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Test")
@Log4j2
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity<String> testPing(){
        log.info("Successfully ping test controller");
        return new ResponseEntity<>("successfully ping", HttpStatus.OK);
    }
}
