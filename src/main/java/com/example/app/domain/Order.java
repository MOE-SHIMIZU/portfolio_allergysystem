package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Order {
	
	private Integer id;
	
	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "^[ァ-タダ-ヶー]*$")
	private String kana;

	@NotNull
	@Range(min = 1)
	private Integer price;
	private String note;
	
	private String img;	
	private User user;
	private Date created;
	
	// 画像のアップロード	
	private MultipartFile upfile;
	
	//	Detailテーブル情報
	List<Detail> ingredientIdList;

	//	商品一覧ページ用
	private String allergyAndIngredient;
	private String ingredientName;
	
	//	詳細ページ用
	private String allergies;
}
