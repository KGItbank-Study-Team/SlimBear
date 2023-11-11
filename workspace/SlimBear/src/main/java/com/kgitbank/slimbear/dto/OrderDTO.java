package com.kgitbank.slimbear.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
	
	private long uid;
	private long mem_uid;
	private Date order_date;
	private String status;
	private int total_price;
	
}
