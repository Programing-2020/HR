package com.nphc.hr.dto;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDTO {
	
	@JsonProperty(value = "id")
	private String id;
	@JsonProperty(value = "login")
	private String login;
	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "salary")
	@Min(value=0)
	private double salary;
	//@DateValue
	@JsonProperty(value = "startDate")
	private String startDate;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + ", startDate="
				+ startDate + "]";
	}

	public EmployeeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeDTO(String id, String login, String name, double salary, String startDate) {
		super();
		this.id = id;
		this.login = login;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
