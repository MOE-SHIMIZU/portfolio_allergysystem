package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.app.validation.AddGroup;
import com.example.app.validation.EditGroup;

import lombok.Data;

@Data
public class FoodCat {

	private Integer id;
	
	@NotBlank(groups = {EditGroup.class})
	private String name;

	@NotBlank(groups = {EditGroup.class})
	@Pattern(regexp = "^[ァ-タダ-ヶー]*$", groups = { EditGroup.class, AddGroup.class })
	private String kana;
	
	private User user;
	private Date created;
					
	
}
