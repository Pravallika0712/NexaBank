package com.bank.controller;

import com.bank.model.FixedDeposit;
import com.bank.service.FDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FDController {

    @Autowired private FDService fdService;

    @PostMapping("/fd/create")
    public FixedDeposit createFD(@RequestHeader("Authorization") String token,
                                 @RequestBody Map<String, String> body) {

    	String userId = token.replace("Bearer ", "");

        return fdService.createFD(
                userId,
                body.get("accountNumber"),
                Double.parseDouble(body.get("amount")),
                Integer.parseInt(body.get("months"))
        );
    }

    @PostMapping("/fd/break")
    public FixedDeposit breakFD(@RequestBody Map<String, String> body) {
        return fdService.breakFD(body.get("fdId"));
    }

    @GetMapping("/fds")
    public List<FixedDeposit> getFDs(@RequestHeader("Authorization") String token) {

    	String userId = token.replace("Bearer ", "");

        return fdService.getUserFDs(userId);
    }
}