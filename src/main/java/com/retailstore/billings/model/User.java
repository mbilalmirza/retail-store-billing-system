package com.retailstore.billings.model;

import lombok.*;

import java.time.LocalDate;

/**
 * Created Date : 11/02/2024
 *
 * @author : Bilal Mirza <bilal.mirza@seamless.se>
 * Purpose      :
 * <p>
 * Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User
{
	private String userId;
	private LocalDate customerSince;
	private UserCategory userCategory;
}
