package com.springboot.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.crud.dto.APIResponse;
import com.springboot.crud.exception.ResourceErrorResponse;
import com.springboot.crud.exception.ResourceNotFoundException;
import com.springboot.crud.model.Employee;
import com.springboot.crud.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//create employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//get employee by Id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		//return employeeRepository.findById(id).get();
		Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id "+id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeData){
		Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id "+id));
		employee.setFirstName(employeeData.getFirstName());
		employee.setLastName(employeeData.getLastName());
		employee.setEmailId(employeeData.getEmailId());
		Employee updateEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	
	//delete employee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id "+id));
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	} 

	//sorting the records
	@GetMapping("/employees/sort/{field}")
	public APIResponse<List<Employee>> findEmployeesWithSorting(@PathVariable String field){
		//return employeeRepository.findAll(Sort.by(field));
		List<Employee> employeeList = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
		return new APIResponse<>(employeeList.size(), employeeList);
	}  
	
	//get records with pagination
	@GetMapping("/employees/pagination/{offset}/{pageSize}")
	public APIResponse<Page<Employee>> findEmployeeWithPagination(@PathVariable int offset, @PathVariable int pageSize){
		Page<Employee> empList = employeeRepository.findAll(PageRequest.of(offset, pageSize)); 
		return new APIResponse<>(empList.getSize(),empList);
	}
	
	//get records with pagination and sorting
	@GetMapping("/employees/pagination/{offset}/{pageSize}/{field}")
	public APIResponse<Page<Employee>> findEmployeeWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
		Page<Employee> empList = employeeRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return new APIResponse<>(empList.getSize(),empList);
	}
	
	/*
	 * //Exception handler method
	 * 
	 * @ExceptionHandler public ResponseEntity<ResourceErrorResponse>
	 * handleException(ResourceNotFoundException ex){ ResourceErrorResponse error =
	 * new ResourceErrorResponse(); error.setStatus(HttpStatus.NOT_FOUND.value());
	 * error.setMessage(ex.getMessage());
	 * error.setTimeStamp(System.currentTimeMillis()); return new
	 * ResponseEntity<ResourceErrorResponse>(error, HttpStatus.NOT_FOUND); }
	 * 
	 * //Exception handler method to catch any type of exception
	 * 
	 * @ExceptionHandler public ResponseEntity<ResourceErrorResponse>
	 * handleException(Exception ex){ ResourceErrorResponse error = new
	 * ResourceErrorResponse(); error.setStatus(HttpStatus.BAD_REQUEST.value());
	 * error.setMessage("Invalid request is passed to get the data");
	 * error.setTimeStamp(System.currentTimeMillis()); return new
	 * ResponseEntity<ResourceErrorResponse>(error, HttpStatus.BAD_REQUEST); }
	 */
}
