package com.example.smartstock.controller;

import com.example.smartstock.model.Alert;
import com.example.smartstock.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping
    public List<Alert> getActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    @PostMapping("/run-check")
    public String runCheckManually() {
        return alertService.runCheckManually();
    }
}