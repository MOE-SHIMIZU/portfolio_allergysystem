package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.AllergiesForm;
import com.example.app.domain.Allergy;
import com.example.app.service.AllergyService;
import com.example.app.validation.AddGroup;
import com.example.app.validation.EditGroup;

@Controller
@RequestMapping("/allergy")
public class AllergyController {

	//	ページネーション　1ページあたりの表示件数
	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private AllergyService allergyService;

	@GetMapping
	public String allergy(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {
		model.addAttribute("AllergyListByPage", allergyService.getAllergyListByPage(page, NUM_PER_PAGE));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allergyService.getTotalPages(NUM_PER_PAGE));
		return "system/allergy/listAllergy";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		AllergiesForm allergiesForm = new AllergiesForm(); 

		List<Allergy> allergies = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			allergies.add(new Allergy());
		}
		allergiesForm.setAllergies(allergies); 

		model.addAttribute("allergiesForm", allergiesForm); 
		return "system/allergy/addAllergy";
	}

	@PostMapping("/add")
	public String addPost(@Validated(AddGroup.class) AllergiesForm allergiesForm,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {

		List<Allergy> allergies = allergiesForm.getAllergies();
		List<FieldError> fieldErrors = new ArrayList<>(); 

		for (int i = 0; i < allergies.size(); i++) {
			Allergy currentAllergy = allergies.get(i);
			
			if (currentAllergy.getName() == null || currentAllergy.getName().isEmpty()) {
				if (currentAllergy.getKana() == null || currentAllergy.getKana().isEmpty()) {
					continue; 
				} else {
					fieldErrors.add(new FieldError("allergies", "allergies[" + i + "].name", "error.name.empty"));
				}
			} else if (currentAllergy.getKana() == null || currentAllergy.getKana().isEmpty()) {
				fieldErrors.add(new FieldError("allergies", "allergies[" + i + "].kana", "error.kana.empty"));
			} 
		
		}

		if (!fieldErrors.isEmpty()){
			for (FieldError error : fieldErrors) {
				errors.rejectValue(error.getField(), error.getDefaultMessage());
			}
			return "system/allergy/addAllergy";
		}

		List<Allergy> validAllergies = allergies.stream()
				.filter(ag -> ag.getName() != null && !ag.getName().isEmpty())
				.filter(ag -> ag.getKana() != null && !ag.getKana().isEmpty())
				.collect(Collectors.toList());

		if (validAllergies == null || validAllergies.isEmpty()) {
			model.addAttribute("errorMessage", "新規食品カテゴリー登録に必要なデータが見当たりません。必要項目を入力してください。");
			return "system/allergy/addAllergy";
		} else if (errors.hasErrors()) {
			return "system/allergy/addAllergy";
		}

		allergyService.addAllergyList(validAllergies);

		ra.addFlashAttribute("statusMessage", "アレルギー項目を追加しました。");
		return "redirect:/allergy";
	}

	@GetMapping("/edit/{id}")
	public String editGet(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("allergy", allergyService.getAllergyById(id));
		return "system/allergy/editAllergy";
	}

	@PostMapping("/edit/{id}")
	public String editPost(
			@PathVariable Integer id, 
			@Validated(EditGroup.class) Allergy allergy, 
			Errors errors,
			RedirectAttributes ra, 
			Model model)
			throws Exception {

		if (errors.hasErrors()) {
			return "system/allergy/editAllergy";
		}

		allergyService.editAllergy(allergy);
		ra.addFlashAttribute("statusMessage", "アレルギー項目を更新しました。");
		return "redirect:/allergy";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) throws Exception {
		allergyService.deleteAllergyById(id);
		ra.addFlashAttribute("statusMessage", "アレルギー項目を削除しました");
		return "redirect:/allergy";
	}

}
