package com.retailstore.billings.controller;

import com.retailstore.billings.model.requests.BillingRequest;
import com.retailstore.billings.services.BillCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillCalculatorService billCalculatorService;

    @Autowired
    public BillingController(BillCalculatorService billCalculatorService) {
        this.billCalculatorService = billCalculatorService;
    }

    @PostMapping("/calculate")
    public BigDecimal calculateBill(@RequestBody BillingRequest billingRequest)
    {
        return billCalculatorService.calculateNetPayableAmount(billingRequest);
    }
}
