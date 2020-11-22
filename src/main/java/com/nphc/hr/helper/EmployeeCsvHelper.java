package com.nphc.hr.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.hr.entity.Employee;
import com.nphc.hr.repository.EmployeeRepository;
import com.nphc.hr.util.DateValidation;
@Component
public class EmployeeCsvHelper {

	@Autowired
	private EmployeeRepository employeeRepository;

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public Map<String, Object> csvToEmp(InputStream is) throws Exception {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Employee> employees = new ArrayList<Employee>();
			List<String> errorMessage = new ArrayList<String>();
			Map<String, Object> result = new HashMap<String, Object>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int postionRecord=1;
			for (CSVRecord csvRecord : csvRecords) {
				postionRecord=postionRecord+1;
				if (!(csvRecord.get("id").startsWith("#"))) {
					// if(!(csvRecord.get("id").contains("#"))) {
					Employee employee = new Employee();
					employee.setId(csvRecord.get("id"));
					employee.setLogin(csvRecord.get("login"));
					employee.setName(csvRecord.get("name"));
					employee.setSalary(Double.parseDouble(csvRecord.get("salary")));
					employee.setStartDate(DateValidation.validateDate(csvRecord.get("startDate")));					
					String valiationResult = validateCsvRecord(employee,true);
					if (valiationResult.length() == 0) {
						employees.add(employee);
					}else {
						errorMessage.add(postionRecord+" record have error "+valiationResult);
					}

				}

			}
			result.put("EMPLOYEELIST", employees);
			result.put("ERROR", errorMessage);

			return result;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public String validateCsvRecord(Employee employee,boolean isNewRecord) {
		String result="" ;
		if (employee.getId() == null || employee.getId().equals("") || employee.getId().length() == 0) {
			result=result+"Id value is null or Blank.";
		} else {
			if (isNewRecord && (result.length()==0) && (employeeRepository.findById(employee.getId()).orElse(null) != null)) {
				result=result+"Employee ID already exists.";
			}
		}
		if ((result.length()==0) && (employee.getName() == null || employee.getName().equals("") || employee.getName().length() == 0)) {
			result=result+"Name value is null or Blank.";
		}
		if ((result.length()==0) && (employee.getLogin() == null || employee.getLogin().equals("") || employee.getLogin().length() == 0)) {
			result=result+"Login value is null or Blank.";
		} else {
			if ((result.length()==0) && (employeeRepository.findByLogin(employee.getLogin()).orElse(null) != null)) {
				result=result+"Employee login not unique.";
			}
		}

		if ((result.length()==0) && (employee.getSalary() < 0.0)) {
			result= result+"Salary is less than Zero.";
		}

		return result;
	}

}
