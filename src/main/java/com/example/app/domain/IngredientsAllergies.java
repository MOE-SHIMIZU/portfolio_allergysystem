package com.example.app.domain;

import lombok.Data;

@Data
public class IngredientsAllergies {
	
	private Integer id;
	private Integer ingredientId;
	private Integer allergyId;

}
