package com.retailstore.billings.model.responses;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.ItemCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;

public class BillingResponse
{
	private List<Item> purchasedItems;
	private BigDecimal totalAmount;
	private static final EnumSet<ItemCategory> NON_DISCOUNTED_CATEGORIES = EnumSet.of(ItemCategory.GROCERIES); // Example

	public BillingResponse()
	{
		this.purchasedItems = new ArrayList<>();
		this.totalAmount = BigDecimal.ZERO;
	}

	public void addItem(Item item)
	{
		purchasedItems.add(item);
		// Automatically update total amount without considering discount here
		totalAmount = totalAmount.add(item.getPrice());
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	// New method to calculate total of non-discounted items
	public BigDecimal getTotalNonDiscountedItemsAmount()
	{
		return purchasedItems.stream()
				.filter(item -> NON_DISCOUNTED_CATEGORIES.contains(item.getCategory()))
				.map(Item::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	// Method to get total of items eligible for discount
	public BigDecimal getTotalDiscountableItemsAmount()
	{
		return purchasedItems.stream()
				.filter(item -> !NON_DISCOUNTED_CATEGORIES.contains(item.getCategory()))
				.map(Item::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean hasNonDiscountedItems()
	{
		return purchasedItems.stream().anyMatch(item -> NON_DISCOUNTED_CATEGORIES.contains(item.getCategory()));
	}

	public List<Item> getPurchasedItems()
	{
		return purchasedItems;
	}
}
