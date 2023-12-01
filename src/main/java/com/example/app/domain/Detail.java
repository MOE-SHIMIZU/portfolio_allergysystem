package com.example.app.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Detail {
	
	private Integer id;
	private Integer ordersId;
	private Integer ingredientId;
	private User user;
	private Date created;
	
}
