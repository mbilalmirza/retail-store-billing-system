package com.retailstore.billings.model.strategies;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.interfaces.DiscountStrategy;
import com.retailstore.billings.model.requests.BillingRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FixedDiscountStrategy implements DiscountStrategy
{
    private final BigDecimal discountPerThreshold;
    private final BigDecimal thresholdAmount;

    public FixedDiscountStrategy(BigDecimal discountPerThreshold, BigDecimal thresholdAmount)
    {
        this.discountPerThreshold = discountPerThreshold;
        this.thresholdAmount = thresholdAmount;
    }

    @Override
    public boolean isApplicable(BillingRequest billingRequest)
    {
        // This discount applies to all bills, hence always true.
        return true;
    }

    @Override
    public BigDecimal calculateDiscount(List<Item> discountableItems)
    {
        BigDecimal totalDiscountableItemsAmount = getTotalDiscountableItemsAmount(discountableItems);
        if(totalDiscountableItemsAmount.compareTo(thresholdAmount) > 0)
        {
            return totalDiscountableItemsAmount.divide(thresholdAmount, RoundingMode.DOWN).multiply(discountPerThreshold);
        }
        return new BigDecimal(0);
    }
}
