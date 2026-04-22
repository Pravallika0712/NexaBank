package com.bank.controller;

import com.bank.service.EMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin("*")
public class EMIController {

    @Autowired
    private EMIService emiService;

    @PostMapping("/calculator")  
    public Map<String, Object> calculate(@RequestBody Map<String, String> body) {

        double principal = Double.parseDouble(body.get("principal"));
        double rate = Double.parseDouble(body.get("rate"));
        int months = Integer.parseInt(body.get("months"));

        return emiService.calculateEMI(principal, rate, months);
    }
}