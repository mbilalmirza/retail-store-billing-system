package com.retailstore.billings.services;

import com.retailstore.billings.model.requests.BillingRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Setter
@Getter
public class BillCalculatorService
{

	private DiscountService discountService;

	@Autowired
	public BillCalculatorService(DiscountService discountService)
	{
		this.discountService = discountService;
	}

	public BigDecimal calculateNetPayableAmount(BillingRequest billingRequest)
	{
		return discountService.calculateNetPayableAmount(billingRequest);
	}


}
