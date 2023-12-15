package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.app.validation.AddGroup;
import com.example.app.validation.EditGroup;

import lombok.Data;

@Data
public class Ingredient {

	private Integer id;
	
	@NotBlank(groups = {EditGroup.class})
	private String name;
	
	@NotBlank(groups = {EditGroup.class})
	@Pattern(regexp = "^[ァ-タダ-ヶー]*$", groups = { EditGroup.class, AddGroup.class })
	private String kana;

	@NotNull(groups = {EditGroup.class})
	private Integer foodCatId;
	
	private User user;
	private Date created;

	//　一覧用
	private String foodCatName;
	private String allergyName;
	
	//	ingredientsAllergies
	private List<IngredientsAllergies> allergyIdList;
	

}
