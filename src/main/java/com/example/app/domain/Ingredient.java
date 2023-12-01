package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.app.validation.EditGroup;

import lombok.Data;

@Data
public class Ingredient {

	private Integer id;
	
	@NotBlank(groups = {EditGroup.class})
	private String name;
	
	@NotBlank(groups = {EditGroup.class})
	@Pattern(regexp = "^[ァ-タダ-ヶー]*$")
	private String kana;

	private Integer foodCatId;
	
	private User user;
	private Date created;

	//　一覧用
	private String foodCatName;
	private String allergyName;
	
	//	ingredientsAllergies
	List<Allergy> allergyIdList;
	

}
