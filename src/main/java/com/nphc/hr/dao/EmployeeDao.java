package com.nphc.hr.dao;

import com.nphc.hr.entity.Employee;
import org.springframework.data.repository.CrudRepository;




public interface EmployeeDao extends CrudRepository<Employee, String> {

}
