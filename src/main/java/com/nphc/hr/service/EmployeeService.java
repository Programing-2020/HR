package com.nphc.hr.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hr.entity.Employee;

@Service
public interface EmployeeService {

	
	public void saveEmp(Employee emp) ;

	public String validateEmp(Employee employee,boolean isNew) ;
	public Employee getEmpoyeeById(String id) ;

	public List<Employee> getAllEmployees() ;
	public Page<Employee> getAllEmployees(Pageable pageable) ;
	public String deleteEmployee(String id);
	public void save(List<Employee> employees);
	public List<String> save(MultipartFile file) throws Exception ;
}