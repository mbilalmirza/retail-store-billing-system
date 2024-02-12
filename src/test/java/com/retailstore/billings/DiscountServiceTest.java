package com.retailstore.billings;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.ItemCategory;
import com.retailstore.billings.model.UserCategory;
import com.retailstore.billings.model.requests.BillingRequest;
import com.retailstore.billings.services.BillCalculatorService;
import com.retailstore.billings.services.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DiscountServiceTest
{

	@InjectMocks
	private BillCalculatorService billCalculatorService;

	@InjectMocks
	private DiscountService discountService;

	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	 void testCalculateNetPayableAmount_withEmployeeDiscount()
	{
		// Setup
		billCalculatorService.setDiscountService(discountService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Laptop", new BigDecimal("1000"), ItemCategory.ELECTRONICS));
		items.add(new Item("Apple", new BigDecimal("1"), ItemCategory.GROCERIES));
		BillingRequest billingRequest = new BillingRequest();
		billingRequest.setPurchasedItems(items);
		billingRequest.setUserId("Test-User");
		billingRequest.setUserCategory(UserCategory.EMPLOYEE.name());
		billingRequest.setCustomerSince(LocalDate.now());

		// Action
		BigDecimal netPayableAmount = billCalculatorService.getDiscountService().calculateNetPayableAmount(billingRequest);

		// Assertion For Employee it should be 300 discount as an Employee and FixedAmount discount of 50$ as 5$ Per 100$ Shopping
		BigDecimal expected = new BigDecimal("1001").subtract(new BigDecimal("300").add(new BigDecimal("50"))); // Total - Discounts
		assertEquals(0, expected.compareTo(netPayableAmount), "Net payable amount should be correctly calculated after discounts.");
	}

	@Test
	void testCalculateNetPayableAmount_withAffiliateDiscount()
	{
		// Setup
		billCalculatorService.setDiscountService(discountService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Mobile", new BigDecimal("500"), ItemCategory.ELECTRONICS));
		items.add(new Item("Milk", new BigDecimal("50"), ItemCategory.GROCERIES));
		items.add(new Item("Rice", new BigDecimal("100"), ItemCategory.GROCERIES));

		BillingRequest billingRequest = new BillingRequest();
		billingRequest.setPurchasedItems(items);
		billingRequest.setUserId("Test-User");
		billingRequest.setUserCategory(UserCategory.AFFILIATE.name());
		billingRequest.setCustomerSince(LocalDate.now());

		// Action
		BigDecimal netPayableAmount = billCalculatorService.calculateNetPayableAmount(billingRequest);

		// Assertion For Affiliate it should be 300 discount as an Employee and FixedAmount discount of 50$ as 5$ Per 100$ Shopping
		BigDecimal expected = new BigDecimal("650").subtract(new BigDecimal("50").add(new BigDecimal("25"))); // Total - Discounts
		assertEquals(0, expected.compareTo(netPayableAmount), "Net payable amount should be correctly calculated after discounts.");
	}

	@Test
	void testCalculateNetPayableAmount_withGeneralDiscount()
	{
		// Setup
		billCalculatorService.setDiscountService(discountService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Television", new BigDecimal("2500"), ItemCategory.ELECTRONICS));
		items.add(new Item("Apple", new BigDecimal("5"), ItemCategory.GROCERIES));
		items.add(new Item("Rice", new BigDecimal("100"), ItemCategory.GROCERIES));
		BillingRequest billingRequest = new BillingRequest();
		billingRequest.setPurchasedItems(items);
		billingRequest.setUserId("Test-User");
		billingRequest.setUserCategory(UserCategory.GENERAL.name());
		billingRequest.setCustomerSince(LocalDate.now());

		// Action
		BigDecimal netPayableAmount = billCalculatorService.calculateNetPayableAmount(billingRequest);

		// Assertion For General Type with Fresh Customer discount should be 0 discount with FixedAmount discount of 125$ as 5$ Per 100$ Shopping
		BigDecimal expected = new BigDecimal("2605").subtract(new BigDecimal("0").add(new BigDecimal("125"))); // Total - Discounts
		assertEquals(0, expected.compareTo(netPayableAmount), "Net payable amount should be correctly calculated after discounts.");
	}

	@Test
	void testCalculateNetPayableAmount_withGeneralDiscount_forLoyalCustomer()
	{
		// Setup
		billCalculatorService.setDiscountService(discountService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Television", new BigDecimal("2500"), ItemCategory.ELECTRONICS));
		items.add(new Item("Apple", new BigDecimal("5"), ItemCategory.GROCERIES));
		items.add(new Item("Rice", new BigDecimal("100"), ItemCategory.GROCERIES));

		BillingRequest billingRequest = new BillingRequest();
		billingRequest.setPurchasedItems(items);
		billingRequest.setUserId("Test-User");
		billingRequest.setUserCategory(UserCategory.GENERAL.name());
		billingRequest.setCustomerSince(LocalDate.now().minusYears(3));

		// Action
		BigDecimal netPayableAmount = billCalculatorService.calculateNetPayableAmount(billingRequest);

		// Assertion For General Type with Loyal Customer discount should be 125 discount with FixedAmount discount of 125$ as 5$ Per 100$ Shopping
		BigDecimal expected = new BigDecimal("2605").subtract(new BigDecimal("125").add(new BigDecimal("125"))); // Total - Discounts
		assertEquals(0, expected.compareTo(netPayableAmount), "Net payable amount should be correctly calculated after discounts.");
	}

	@Test
	void testCalculateNetPayableAmount_withGeneralDiscount_WithNoDiscount()
	{
		// Setup
		billCalculatorService.setDiscountService(discountService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Remote", new BigDecimal("90"), ItemCategory.ELECTRONICS));
		items.add(new Item("Apple", new BigDecimal("5"), ItemCategory.GROCERIES));
		Item riceItem = new Item();
		riceItem.setCategory(ItemCategory.GROCERIES);
		riceItem.setPrice(new BigDecimal("100"));
		riceItem.setItemId("Rice");
		items.add(riceItem);

		BillingRequest billingRequest = new BillingRequest();
		billingRequest.setPurchasedItems(items);
		billingRequest.setUserId("Test-User");
		billingRequest.setUserCategory(UserCategory.GENERAL.name());
		billingRequest.setCustomerSince(LocalDate.now().minusYears(1));

		// Action
		BigDecimal netPayableAmount = billCalculatorService.calculateNetPayableAmount(billingRequest);

		// Assertion For General Type with Loyal Customer discount should be 0 discount with FixedAmount discount of 0
		BigDecimal expected = new BigDecimal("195"); // Total - Discounts
		assertEquals(0, expected.compareTo(netPayableAmount), "Net payable amount should be correctly calculated after discounts.");
	}
}
