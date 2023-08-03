package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CustomerRepository;

@Controller
public class CustomerController {
	@Autowired
	Account account;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/profile")
	public String prof(Model model) {
		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());
		model.addAttribute("categories", categoryList);

		Customer cusotomer = customerRepository.findById(account.getId()).get();
		model.addAttribute("customer", cusotomer);

		return "profile";
	}

	@PostMapping("/profile/{id}")
	public String profEdit(@PathVariable("id") Integer id,
			@RequestParam("name") String name,
			@RequestParam("userName") String userName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		int flag = 0;

		Customer customer = new Customer(id, name, userName, email, password);
		if (name == null || name.length() == 0) {
			model.addAttribute("errorName", "名前は必須です");
			flag = 1;
		}
		if (userName == null || userName.length() == 0) {
			model.addAttribute("errorUser", "ユーザ名は必須です");
			flag = 1;
		} else if (userName.contains("@")) {
			model.addAttribute("errorUser", "ユーザ名に「@」は使用できません");
			flag = 1;
		}
		if (email == null || email.length() == 0) {
			model.addAttribute("errorMail", "メールアドレスは必須です");
			flag = 1;
		}
		if (password == null || password.length() == 0) {
			model.addAttribute("errorPass", "パスワードは必須です");
			flag = 1;
		}

		if (flag == 1) {
			model.addAttribute("customer", customer);
			model.addAttribute("flag", flag);
			return "profile";
		}

		customerRepository.save(customer);

		account.setName(userName);

		return "redirect:/profile";
	}

	@GetMapping("/request")
	public String request() {
		return "requestForm";
	}

	@PostMapping("/request")
	public String post(@RequestParam("requestDetail") String detail,
			RedirectAttributes redirectAttributes) {
		List<String> requestList = new ArrayList<>();

		requestList.add(detail);

		redirectAttributes.addFlashAttribute("success", "お問い合わせを受け付けました");

		return "redirect:/request";
	}
}
