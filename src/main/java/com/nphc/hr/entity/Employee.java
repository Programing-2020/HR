package com.nphc.hr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = {
	      @UniqueConstraint(columnNames = "login", name = "uniqueloginConstraint")})
public class Employee implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	
    private String id;	
    private String login;
	
    private String name;
	private double salary;
	@Temporal(TemporalType.DATE)
    private Date startDate;
    
    
	@Override
	public String toString() {
		return "Employee [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + ", startDate="
				+ startDate + "]";
	}
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String id, String login, String name, double salary, Date startDate) {
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    

    
}
