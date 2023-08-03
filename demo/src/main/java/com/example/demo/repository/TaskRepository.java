package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	List<Task> findByStateAndCustomerId(Integer state, Integer customerId);

	List<Task> findByStateAndCustomerIdOrderByPriorityAscDeadlineAsc(Integer state, Integer customerId);

	List<Task> findByCategoryIdAndStateAndCustomerIdOrderByPriorityAscDeadlineAsc(Integer id, Integer state,
			Integer customerId);

	List<Task> findByStateAndFlagAndCustomerIdOrderByPriorityAscDeadlineAsc(Integer state, Integer flag,
			Integer customerId);

	List<Task> findByCategoryIdAndStateAndFlagAndCustomerIdOrderByPriorityAscDeadlineAsc(Integer id, Integer state,
			Integer flag, Integer customerId);
}
