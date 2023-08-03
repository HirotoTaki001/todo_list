package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired
	Account account;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/categories")
	public String index(@ModelAttribute(value = "error") String error,
			Model model) {
		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());
		model.addAttribute("categories", categoryList);
		model.addAttribute("error", error);
		return "categories";
	}

	@PostMapping("/categories/add")
	public String store(@RequestParam(value = "name", defaultValue = "") String name,
			RedirectAttributes redirectAttributes) {
		Category category = new Category(name, account.getId());

		if (name == null || name.length() == 0) {
			redirectAttributes.addFlashAttribute("error", "カテゴリー名を入力してください");
		} else {
			categoryRepository.save(category);
		}

		return "redirect:/categories";
	}

	@GetMapping("/categories/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Category category = categoryRepository.findById(id).get();
		model.addAttribute("category", category);
		return "editCategory";
	}

	@PostMapping("/categories/{id}/edit")
	public String update(@PathVariable("id") Integer id,
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model) {
		Category category = new Category(id, name, account.getId());
		categoryRepository.save(category);
		return "redirect:/categories";
	}

	@PostMapping("/categories/{id}/delete")
	public String delete(@PathVariable("id") Integer id, Model model) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}
}