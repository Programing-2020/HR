package com.nphc.hr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hr.entity.Employee;
import com.nphc.hr.helper.EmployeeCsvHelper;
import com.nphc.hr.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeCsvHelper employeeCsvHelper;

	public void saveEmp(Employee emp) {
		try {
			employeeRepository.save(emp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public String validateEmp(Employee employee,boolean isNew) {

		return employeeCsvHelper.validateCsvRecord(employee, isNew);
	}

	public Employee getEmpoyeeById(String id) {
		return employeeRepository.findById(id).orElse(null);
	}

	public List<Employee> getAllEmployees() {

		return employeeRepository.findAll();
	}

	public Page<Employee> getAllEmployees(Pageable pageable) {

		return employeeRepository.findAll(pageable);
	}

	public String deleteEmployee(String id) {
		String message = "";
		Employee emp = employeeRepository.findById(id).orElse(null);
		if (emp != null) {
			employeeRepository.delete(emp);
			message = "Employee deleted";
		} else {
			message = "Bad input - no such employee";
		}

		return message;

	}

	public void save(List<Employee> employees) {
		employeeRepository.saveAll(employees);
	}

	public List<String> save(MultipartFile file) {
		List<Employee> employees = new ArrayList<>();
		List<String> errorMessage = new ArrayList<>();

		try {
			Map<String, Object> csvMap = employeeCsvHelper.csvToEmp(file.getInputStream());
			for (String i : csvMap.keySet()) {
				if (csvMap.get(i) instanceof List) {
					List<Object> list = (List<Object>) csvMap.get(i);
					for (Object obj : (List<Object>) csvMap.get(i)) {
						if (obj instanceof Employee) {
							employees = (List<Employee>) csvMap.get(i);
							break;
						}
						if (obj instanceof String) {
							errorMessage = (List<String>) csvMap.get(i);
							break;
						}
					}

				}
				if (csvMap.get(i) instanceof List) {
					errorMessage = (List<String>) csvMap.get(i);
				}
			}
			if (errorMessage.size()==0 && employees.size() > 0) {
				
				employeeRepository.saveAll(employees);
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
		return errorMessage;
	}
}