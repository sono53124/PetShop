package com.koreait.petshop.model.domain;

import lombok.Data;

@Data
public class OrderSummary {
	private int order_summeary_id;
	private int member_id;
	private int total_price;
	private String orderdate;
	private int order_state_id;
	private int paymethod_id;
	
}