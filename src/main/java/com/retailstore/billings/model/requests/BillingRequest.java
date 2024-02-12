package com.retailstore.billings.model.requests;

import com.retailstore.billings.model.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
public class BillingRequest
{
	private String userId;
	private String userCategory;
	private LocalDate customerSince;
	private List<Item> purchasedItems;
}
