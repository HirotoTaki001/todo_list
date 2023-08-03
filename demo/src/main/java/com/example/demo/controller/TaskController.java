package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {
	@Autowired
	Account account;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	CategoryRepository categoryRepository;

	// メイン画面の表示
	@GetMapping("/tasks")
	public String index(@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@ModelAttribute(value = "add") Task add,
			@ModelAttribute(value = "errorList") String errorList,
			Model model) {

		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());
		model.addAttribute("categories", categoryList);
		int over = 0;

		List<Task> taskList = null;

		taskList = taskRepository.findByStateAndCustomerId(1, account.getId());
		for (Task task : taskList) {
			if (task.getLine().isBefore(LocalDate.now()) && task.getVariety() == 2) {
				task.setState(0);
				taskRepository.save(task);
			}
		}

		if (categoryId == null) {
			taskList = taskRepository.findByStateAndCustomerIdOrderByPriorityAscDeadlineAsc(0, account.getId());
		} else {
			taskList = taskRepository.findByCategoryIdAndStateAndCustomerIdOrderByPriorityAscDeadlineAsc(categoryId, 0,
					account.getId());
		}

		for (Task task : taskList) {
			if (task.getLine().isBefore(LocalDate.now()) && task.getVariety() != 1) {
				if (task.getVariety() == 2) {
					switch (task.getHz()) {
					case 7:
						task.setDeadline(task.getLine().plusDays(7));
						break;
					case 30:
						task.setDeadline(task.getLine().plusMonths(1));
						break;
					case 365:
						task.setDeadline(task.getLine().plusYears(1));
						break;
					default:
						task.setDeadline(LocalDate.now());
					}
					task.setFlag(0);
				} else {
					task.setFlag(1);
					over++;
				}
			} else {
				task.setFlag(0);
			}
			taskRepository.save(task);
		}

		if (categoryId == null) {
			taskList = taskRepository.findByStateAndFlagAndCustomerIdOrderByPriorityAscDeadlineAsc(0, 0,
					account.getId());
		} else {
			taskList = taskRepository.findByCategoryIdAndStateAndFlagAndCustomerIdOrderByPriorityAscDeadlineAsc(
					categoryId, 0, 0, account.getId());
			model.addAttribute("categoryId", categoryId);
		}

		model.addAttribute("tasks", taskList);
		model.addAttribute("add", add);
		model.addAttribute("errorList", errorList);
		model.addAttribute("over", over);

		return "tasks";
	}

	// タスク登録処理
	@PostMapping("/tasks/add")
	public String store(@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadlines,
			@RequestParam(name = "priority", defaultValue = "") Integer priority,
			@RequestParam("state") Integer state,
			@RequestParam(name = "detail", defaultValue = "詳細無し") String detail,
			@RequestParam(name = "variety", defaultValue = "5") Integer variety,
			@RequestParam("hz") Integer hz,
			RedirectAttributes redirectAttributes) {
		List<String> addErrorList = new ArrayList<>();
		Task task = new Task();
		if (variety == 1) {
			deadline = LocalDate.now().plusYears(30);
			task = new Task(categoryId, account.getId(), name, deadline, priority, state, detail, variety, 0);
		} else if (variety == 2) {
			task = new Task(categoryId, account.getId(), name, deadlines, priority, state, detail, variety, hz);
		} else {
			task = new Task(categoryId, account.getId(), name, deadline, priority, state, detail, variety, 0);
		}

		if (name == null || name.length() == 0) {
			addErrorList.add("タスク内容を入力してください");
		}
		if (deadline != null && (deadline.isBefore(LocalDate.now())) && variety == 3) {
			addErrorList.add("タスク期限には当日以降を指定してください");
		}
		if (variety == 5) {
			addErrorList.add("タスク期限の有無を選択してください");
		}

		if (addErrorList.size() == 0) {
			taskRepository.save(task);
			task = new Task();
		}

		redirectAttributes.addFlashAttribute("errorList", addErrorList);
		redirectAttributes.addFlashAttribute("add", task);

		return "redirect:/tasks";
	}

	// タスク編集画面の表示
	@GetMapping("/tasks/{id}/edit")
	public String edit(@PathVariable("id") Integer id,
			Model model) {
		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());
		model.addAttribute("categories", categoryList);

		Task task = taskRepository.findById(id).get();

		model.addAttribute("task", task);

		return "taskEdit";
	}

	// タスク編集処理
	@PostMapping("/tasks/{id}/edit")
	public String update(@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadlines,
			@RequestParam("priority") Integer priority,
			@RequestParam(name = "state", defaultValue = "0") Integer state,
			@RequestParam(name = "detail", defaultValue = "詳細無し") String detail,
			@RequestParam("variety") Integer variety,
			@RequestParam("hz") Integer hz,
			Model model) {
		Task task = new Task();
		if (variety == 1) {
			deadline = LocalDate.now().plusYears(30);
			task = new Task(id, categoryId, account.getId(), name, deadline, priority, state, detail, variety, 0);
		} else if (variety == 2) {
			task = new Task(id, categoryId, account.getId(), name, deadlines, priority, state, detail, variety, hz);
		} else {
			task = new Task(id, categoryId, account.getId(), name, deadline, priority, state, detail, variety, 0);
		}

		taskRepository.save(task);

		return "redirect:/tasks";
	}

	// タスク状態遷移処理
	@PostMapping("/tasks/{id}/change")
	public String change(@PathVariable("id") Integer id,
			Model model) {
		Task task = taskRepository.findById(id).get();

		switch (task.getState()) {
		case 0:
			task.setState(1);
			break;
		case 1:
			task.setState(0);
			break;
		}

		taskRepository.save(task);

		if (task.getState() == 0) {
			return "redirect:/tasks/checked";
		}
		return "redirect:/tasks";
	}

	// 達成済みタスク一覧表示
	@GetMapping("/tasks/checked")
	public String checkrd(Model model) {
		List<Task> taskList = taskRepository.findByStateAndCustomerIdOrderByPriorityAscDeadlineAsc(1, account.getId());
		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());

		model.addAttribute("tasks", taskList);
		model.addAttribute("categories", categoryList);

		return "taskChecked";
	}

	// タスク削除処理
	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable("id") Integer id,
			Model model) {
		Task task = taskRepository.findById(id).get();
		taskRepository.deleteById(id);

		if (task.getState() == 1) {
			return "redirect:/tasks/checked";
		}

		return "redirect:/tasks";
	}

	// 期限切れタスク一覧表示
	@GetMapping("/tasks/over")
	public String overed(Model model) {
		List<Task> taskList = taskRepository.findByStateAndFlagAndCustomerIdOrderByPriorityAscDeadlineAsc(0, 1,
				account.getId());
		List<Category> categoryList = categoryRepository.findByCustomerId(account.getId());

		model.addAttribute("tasks", taskList);
		model.addAttribute("categories", categoryList);

		return "overTasks";
	}
}