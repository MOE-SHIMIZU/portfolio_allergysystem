package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Detail;
import com.example.app.domain.Order;
import com.example.app.service.FoodCatService;
import com.example.app.service.IngredientService;
import com.example.app.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrdersController {

	//	ページネーション　1ページあたりの表示件数
	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private OrderService orderService;

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private FoodCatService foodCatService;

	@GetMapping
	public String orders(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {

		model.addAttribute("ordereListByPage", orderService.getOrderListByPage(page, NUM_PER_PAGE));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", orderService.getTotalPages(NUM_PER_PAGE));

		return "system/order/listOrder";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {

		model.addAttribute("foodCatList", foodCatService.getFoodCatList());
		model.addAttribute("ingredients", ingredientService.getIngredientFoodCat());

		Order orderform = new Order();
		List<Detail> list = new ArrayList<>();
		Detail detail = new Detail();

		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);
		list.add(detail);

		orderform.setIngredientIdList(list);
		model.addAttribute("order", orderform);
		return "system/order/addOrder";
	}

	@PostMapping("/add")
	public String addPost(
			@Validated Order order,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {

		MultipartFile upfile = order.getUpfile();
		if (!upfile.isEmpty()) {
			String type = upfile.getContentType();
			if (!type.startsWith("image/")) {
				errors.rejectValue("upfileError", "error.not_image_file");
			}

		}

		List<Detail> validIngredients = order.getIngredientIdList().stream()
				.filter(ing -> ing.getIngredientId() != null)
				.collect(Collectors.toList());

		if (validIngredients == null) {
			errors.rejectValue("ingredientError", "error.not_ingredient");
		}

		if (errors.hasErrors()) {
			model.addAttribute("foodCatList", foodCatService.getFoodCatList());
			model.addAttribute("ingredients", ingredientService.getIngredientFoodCat());

			return "system/order/addOrder";

		}

		order.setIngredientIdList(validIngredients);
		orderService.addOrder(order);

		ra.addFlashAttribute("statusMessage", "商品項目を追加しました。");
		return "redirect:/order";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("order", orderService.getOrderById(id));
		model.addAttribute("details", orderService.getOrderDetailById(id));
		return "system/order/detailOrder";

	}

}
