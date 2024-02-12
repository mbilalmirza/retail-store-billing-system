package com.retailstore.billings.model.interfaces;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.requests.BillingRequest;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountStrategy
{
	boolean isApplicable(BillingRequest billingRequest);

	BigDecimal calculateDiscount(List<Item> discountableItems);

	// Method to get total of items eligible for discount
	default BigDecimal getTotalDiscountableItemsAmount(List<Item> purchasedItems)
	{
		return purchasedItems.stream()
				.map(Item::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
