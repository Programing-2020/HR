package com.nphc.hr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nphc.hr.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	 
	 @Query("SELECT e FROM Employee e WHERE e.login = ?1 ")
	 Optional<Employee> findByLogin(String login);

	 
}
