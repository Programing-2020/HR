package com.nphc.hr.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nphc.hr.dto.EmployeeDTO;
import com.nphc.hr.entity.Employee;

@Service
public class ConverterService {

	@Autowired
	private ModelMapper modelMapper;

// convert entity to dto	
	public EmployeeDTO convertToDto(Employee employeeObject) {
		return modelMapper.map(employeeObject, EmployeeDTO.class);
	}

// convert dto to entity
	public Employee convertToEntity(EmployeeDTO employeedto) {
		return modelMapper.map(employeedto, Employee.class);
	}

}
