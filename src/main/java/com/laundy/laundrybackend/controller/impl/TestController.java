package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.controller.api.TestInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestInterface {
    @Override
    public ResponseEntity<?> testTerra() {
        return ResponseEntity.ok("6699");
    }
}
