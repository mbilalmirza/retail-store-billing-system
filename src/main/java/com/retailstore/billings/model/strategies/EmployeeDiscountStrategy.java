package com.retailstore.billings.model.strategies;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.UserCategory;
import com.retailstore.billings.model.interfaces.DiscountStrategy;
import com.retailstore.billings.model.requests.BillingRequest;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDiscountStrategy implements DiscountStrategy
{
	private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.30"); // 30% Discount

	@Override
	public boolean isApplicable(BillingRequest billingRequest)
	{
		return UserCategory.valueOf(billingRequest.getUserCategory()) == UserCategory.EMPLOYEE;
	}

	@Override
	public BigDecimal calculateDiscount(List<Item> discountableItems)
	{
		return getTotalDiscountableItemsAmount(discountableItems).multiply(DISCOUNT_RATE);
	}
}
