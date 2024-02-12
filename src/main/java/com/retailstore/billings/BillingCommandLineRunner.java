package com.retailstore.billings;

import com.retailstore.billings.model.Item;
import com.retailstore.billings.model.ItemCategory;
import com.retailstore.billings.model.UserCategory;
import com.retailstore.billings.model.requests.BillingRequest;
import com.retailstore.billings.services.BillCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class BillingCommandLineRunner implements CommandLineRunner
{

	public static final Logger LOGGER = LoggerFactory.getLogger(BillingCommandLineRunner.class);
	private final BillCalculatorService billCalculatorService;

	public BillingCommandLineRunner(BillCalculatorService billCalculatorService)
	{
		this.billCalculatorService = billCalculatorService;
	}

	@Override
	public void run(String... args)
	{
		// Create a billing Request

		BillingRequest request = new BillingRequest();
		request.setUserCategory(UserCategory.LOYAL_CUSTOMER.name());
		request.setCustomerSince(LocalDate.now().minusYears(3)); // A loyal customer for 3 years

		Item jeans = new Item("Jeans", new BigDecimal("79.99"), ItemCategory.CLOTHING);
		Item milk = new Item("Milk", new BigDecimal("3.49"), ItemCategory.GROCERIES);
		request.setPurchasedItems(Arrays.asList(jeans, milk));

		// Calculate net payable amount
		BigDecimal netPayable = billCalculatorService.calculateNetPayableAmount(request);
		LOGGER.info("Net payable amount: ${}", netPayable);
	}
}
