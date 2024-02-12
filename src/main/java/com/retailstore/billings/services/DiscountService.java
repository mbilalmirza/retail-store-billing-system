package com.retailstore.billings.services;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.ItemCategory;
import com.retailstore.billings.model.interfaces.DiscountStrategy;
import com.retailstore.billings.model.requests.BillingRequest;
import com.retailstore.billings.model.strategies.AffiliateDiscountStrategy;
import com.retailstore.billings.model.strategies.EmployeeDiscountStrategy;
import com.retailstore.billings.model.strategies.FixedDiscountStrategy;
import com.retailstore.billings.model.strategies.LoyalCustomerDiscountStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class DiscountService
{
	private final List<DiscountStrategy> discountStrategies;
	private static final EnumSet<ItemCategory> NON_DISCOUNTED_CATEGORIES = EnumSet.of(ItemCategory.GROCERIES);

	public DiscountService()
	{
		discountStrategies = new ArrayList<>();

		// Initialize with all available discount strategies
		discountStrategies.add(new EmployeeDiscountStrategy());
		discountStrategies.add(new AffiliateDiscountStrategy());
		discountStrategies.add(new LoyalCustomerDiscountStrategy());

		// Parameterize the fixed discount strategy
        // This approach allows the FixedDiscountStrategy to be easily configured with different discount rates and thresholds.
		BigDecimal discountPerThreshold = new BigDecimal("5"); // $5 discount
		BigDecimal thresholdAmount = new BigDecimal("100"); // For every $100 spent
		discountStrategies.add(new FixedDiscountStrategy(discountPerThreshold, thresholdAmount));
	}

	public BigDecimal calculateNetPayableAmount(BillingRequest billingRequest)
	{
		BigDecimal totalDiscount = BigDecimal.ZERO;

		for (DiscountStrategy strategy : discountStrategies)
		{
			if (strategy.isApplicable(billingRequest))
			{
				totalDiscount = totalDiscount.add(strategy.calculateDiscount(getDiscountableItems(billingRequest.getPurchasedItems())));
			}
		}

		return getTotalPayableAmount(billingRequest.getPurchasedItems()).subtract(totalDiscount);
	}

	public List<Item> getDiscountableItems(List<Item> purchasedItems)
	{
		return purchasedItems.stream()
				.filter(item -> !NON_DISCOUNTED_CATEGORIES.contains(item.getCategory()))
				.toList();
	}

	public BigDecimal getTotalPayableAmount(List<Item> purchasedItems)
	{
		return purchasedItems.stream()
				.map(Item::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
