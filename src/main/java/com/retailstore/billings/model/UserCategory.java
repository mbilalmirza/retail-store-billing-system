package com.retailstore.billings.model;

/**
 * Created Date : 11/02/2024
 * <p>
 * Purpose : It holds common categories of User
 * <p>
 * @author : Muhammad Bilal  <m.bilaaal@gmail.com>
 */

public enum UserCategory
{
	EMPLOYEE,
	AFFILIATE,
	LOYAL_CUSTOMER, // Customers for over 2 years
	GENERAL; // For customers that don't fall into the above categories
}
