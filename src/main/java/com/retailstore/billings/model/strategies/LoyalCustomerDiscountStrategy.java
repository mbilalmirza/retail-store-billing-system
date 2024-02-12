package com.retailstore.billings.model.strategies;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.interfaces.DiscountStrategy;
import com.retailstore.billings.model.requests.BillingRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class LoyalCustomerDiscountStrategy implements DiscountStrategy
{
	private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05"); // 5% Discount

	@Override
	public boolean isApplicable(BillingRequest billingRequest)
	{
		LocalDate now = LocalDate.now();
		Period period = Period.between(billingRequest.getCustomerSince(), now);
		return period.getYears() >= 2;
	}

	@Override
	public BigDecimal calculateDiscount(List<Item> discountableItems)
	{
		// Only discount items that are eligible for discounts
		return getTotalDiscountableItemsAmount(discountableItems).multiply(DISCOUNT_RATE);
	}
}
