package com.example.app.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class OrderForm {

	//	Ordersテーブル情報
	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "^[ァ-タダ-ヶー]*$")
	private String kana;

	@NotNull
	@Range(min = 1)
	private Integer price;
	private String note;

	// 画像のアップロード	
	private MultipartFile upfile;

	//	Detailテーブル情報
	List<Detail> ingredientIdList;

}
