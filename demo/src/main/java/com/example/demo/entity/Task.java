package com.example.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "customer_id")
	private Integer customerId;

	private final static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	private String name;
	private LocalDate deadline;
	private Integer priority;
	private Integer state;
	private Integer flag;
	private String detail;
	private Integer variety;
	private Integer hz;

	public Task() {
	}

	public Task(Integer categoryId, Integer customerId, String name, LocalDate limit, Integer priority, Integer state,
			String detail,
			Integer variety, Integer hz) {
		this.categoryId = categoryId;
		this.customerId = customerId;
		this.name = name;
		this.deadline = limit;
		this.priority = priority;
		this.state = state;
		this.detail = detail;
		this.variety = variety;
		this.hz = hz;
	}

	public Task(Integer id, Integer categoryId, Integer customerId, String name, LocalDate limit, Integer priority,
			Integer state,
			String detail, Integer variety, Integer hz) {
		this.id = id;
		this.categoryId = categoryId;
		this.customerId = customerId;
		this.name = name;
		this.deadline = limit;
		this.priority = priority;
		this.state = state;
		this.detail = detail;
		this.variety = variety;
		this.hz = hz;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public LocalDate getLine() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getDeadline() {
		return deadline.format(FMT);
	}

	public Integer getPriority() {
		return priority;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getDetail() {
		return detail;
	}

	public Integer getVariety() {
		return variety;
	}

	public Integer getHz() {
		return hz;
	}
}