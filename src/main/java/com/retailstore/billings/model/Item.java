package com.retailstore.billings.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created Date : 11/02/2024
 * Time         : 2:06 pm
 *
 * @author : Bilal Mirza <bilal.mirza@seamless.se>
 * Purpose      :
 * <p>
 * Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item
{
		private String itemId;
		private BigDecimal price;
		private ItemCategory category;
}
