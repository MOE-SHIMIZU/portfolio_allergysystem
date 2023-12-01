package com.example.app.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {

	private Integer id;
	private String name;
	private String kana;
	private Integer price;
	private String img;
	private String note;
	private User user;
	private Date created;

	List<Detail> detailIngredientList;

	//	商品一覧ページ用
	private String allergyAndIngredient;
	private String ingredientName;
	
	//	詳細ページ用
	private String allergies;
}
