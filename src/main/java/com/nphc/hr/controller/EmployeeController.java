package com.nphc.hr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hr.dto.EmployeeDTO;
import com.nphc.hr.entity.Employee;
import com.nphc.hr.helper.EmployeeCsvHelper;
import com.nphc.hr.service.ConverterService;
import com.nphc.hr.service.EmployeeService;

@RestController
@RequestMapping("/users")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ConverterService converterService;
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUserinfo(@PageableDefault(page = 0, size = 4) Pageable pageRequest) {
		Page<Employee> employeePage = employeeService.getAllEmployees(pageRequest);
		List<Employee> employeeResultList = employeePage.getContent();
		List<EmployeeDTO> empDtoList;
		empDtoList= employeeResultList.stream().map(empObj -> {
			 System.out.println("caleed");
		   return converterService.convertToDto(empObj);		  
		 }).collect(Collectors.toList());;
		 
		// alternate way through method reference operator
	//	List<EmployeeDTO> empDtoList=employeeResultList.stream().map(converterService::convertToDto).collect(Collectors.toList());
		Page<Employee> userDtoPage = new PageImpl<>(employeeResultList, pageRequest, employeePage.getTotalElements());
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("results", new PageImpl<>(employeeResultList, pageRequest,
		// employeePage.getTotalElements()));
		map.put("results", empDtoList);
		//map.put("results", employeeResultList);
		return ResponseEntity.status(HttpStatus.OK).body(map);
		// return userDtoPage;
	}

	@GetMapping(value = "/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserById(@PathVariable("Id") String id) {
		Employee employee = employeeService.getEmpoyeeById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (employee == null) {
			map.put("results", "No Such Employee Exisit with " + id + " Id");
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
		} else {
			map.put("results", employee);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
		try {
			SimpleDateFormat logformat1 = new SimpleDateFormat("dd-MMM-yy");
			SimpleDateFormat logformat2 = new SimpleDateFormat("yyyy-MM-dd");

			Employee employee = new Employee();
			employee.setId(employeeDTO.getId());
			employee.setLogin(employeeDTO.getLogin());
			employee.setName(employeeDTO.getName());
			employee.setSalary(employeeDTO.getSalary());
			java.util.Date date = null;
			try {
				date = new Date(logformat2.parse(employeeDTO.getStartDate()).getTime());
				employee.setStartDate(date);

			} catch (ParseException e) {
				try {
					date = new Date(logformat2.parse(employeeDTO.getStartDate()).getTime());
					employee.setStartDate(date);
				} catch (ParseException p) {
					p.getMessage();
				}
			}
			String validateResult = employeeService.validateEmp(employee, true);
			Map<String, Object> map = new HashMap<String, Object>();
			if (validateResult.length() == 0) {
				employeeService.saveEmp(employee);
				map.put("results", "Successfully created");
			} else {
				map.put("results", Arrays.asList(validateResult));
			}

			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error");
		}
	}

	@DeleteMapping(value = "/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteUserById(@PathVariable("Id") String id) {
		String message = employeeService.deleteEmployee(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (message.equalsIgnoreCase("Employee deleted")) {
			map.put("results", message + " " + id);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} else {
			map.put("results", message);
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
		}
	}

	@PatchMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		try {
			SimpleDateFormat logformat1 = new SimpleDateFormat("dd-MMM-yy");
			SimpleDateFormat logformat2 = new SimpleDateFormat("yyyy-MM-dd");
			Employee employeeAvailable = employeeService.getEmpoyeeById(employeeDTO.getId());

			Employee employee = new Employee();
			employee.setId(employeeDTO.getId());
			employee.setLogin(employeeDTO.getLogin());
			employee.setName(employeeDTO.getName());
			employee.setSalary(employeeDTO.getSalary());
			java.util.Date date = null;
			try {
				date = new Date(logformat2.parse(employeeDTO.getStartDate()).getTime());
				employee.setStartDate(date);

			} catch (ParseException e) {
				try {
					date = new Date(logformat2.parse(employeeDTO.getStartDate()).getTime());
					employee.setStartDate(date);
				} catch (ParseException p) {
					p.getMessage();
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			String validateResult = "";
			if (employeeAvailable != null) {
				validateResult = employeeService.validateEmp(employee, false);
				if (validateResult.length() == 0) {
					employeeService.saveEmp(employee);
					map.put("results", "Successfully updated");
				} else {
					map.put("results", Arrays.asList(validateResult));
				}
			} else
				map.put("eroror", "Employee not available, we can't update");

			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error");
		}
	}

	@PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		String message = "";
		if (EmployeeCsvHelper.hasCSVFormat(file)) {
			try {

				List<String> listMessage = employeeService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				Map<String, Object> map = new HashMap<String, Object>();
				if (listMessage.size() > 0)
					map.put("Errors", listMessage);
				else
					map.put("results", message);
				return ResponseEntity.status(HttpStatus.OK).body(map);
			} catch (Exception e) {
				e.printStackTrace();
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

}