package com.bank.controller;

import com.bank.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AnalyticsController {

    @Autowired private AnalyticsService analyticsService;

    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics(@RequestHeader("Authorization") String token) {
        String userId = token.replace("Bearer ", "");  
        return analyticsService.analytics(userId);
    }

    @GetMapping("/savings-growth")
    public Map<String, Object> getSavingsGrowth(@RequestHeader("Authorization") String token) {
        String userId = token.replace("Bearer ", "");  
        return analyticsService.getSavingsGrowth(userId);
    }
}