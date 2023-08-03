package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class AccountController {
	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping({ "/", "/login", "/logout" })
	public String index(@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		session.invalidate();

		if (error.equals("notLoggedIn")) {
			model.addAttribute("massage", "ログインしてください");
		}
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		List<Customer> customerList = null;

		if (name.contains("@")) {
			customerList = customerRepository.findByEmailAndPassword(name, password);
		} else {
			customerList = customerRepository.findByUserNameAndPassword(name, password);
		}

		if (name == null || name.length() == 0 || password == null || password.length() == 0) {
			model.addAttribute("message", "メールアドレスもしくはユーザ名とパスワードを入力してください");
			model.addAttribute("email", name);
			return "login";
		}

		if (customerList == null || customerList.size() == 0 && name.contains("@")) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			model.addAttribute("email", name);
			return "login";
		} else if (customerList == null || customerList.size() == 0) {
			model.addAttribute("message", "ユーザ名とパスワードが一致しませんでした");
			model.addAttribute("email", name);
			return "login";
		}

		Customer customer = customerList.get(0);
		account.setName(customer.getUserName());
		account.setId(customer.getId());

		return "redirect:/tasks";
	}

	@GetMapping("/account")
	public String create() {
		return "accountForm";
	}

	@PostMapping("/account")
	public String store(@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "userName", defaultValue = "") String userName,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		Customer customer = new Customer(name, userName, email, password);
		int flag = 0;

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
			model.addAttribute("name", name);
			model.addAttribute("user", userName);
			model.addAttribute("email", email);
			return "accountForm";
		}

		customerRepository.save(customer);

		List<Category> newCust = new ArrayList<>();
		List<String> cate = new ArrayList<>();

		cate.add("仕事");
		cate.add("趣味");
		cate.add("生活");
		cate.add("その他");

		for (String c : cate) {
			newCust.add(new Category(c, customer.getId()));
		}

		for (Category ca : newCust) {
			categoryRepository.save(ca);
		}
		return "redirect:/login";
	}
}
