package com.laundy.laundrybackend.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("test/")
@CrossOrigin("*")
public interface TestInterface {
    @GetMapping("test")
    ResponseEntity<?> testTerra();
}
