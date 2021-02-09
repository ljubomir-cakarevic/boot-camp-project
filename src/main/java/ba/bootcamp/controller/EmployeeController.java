package ba.bootcamp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.request.EmployeeRequest;
import ba.bootcamp.response.EmployeeResponse;
import ba.bootcamp.services.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/data")
public class EmployeeController {

	private final static Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	EmployeeService employeeService;

	// get all employees REST API
	
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

		LOGGER.info("---------------EmployeeController.getAllEmployees()");

		List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
		
		List<EmployeeResponse> employeeResponseList = employeeDtoList.stream()
				.map(e -> {
					EmployeeResponse employeeResponse = new EmployeeResponse();
					BeanUtils.copyProperties(e, employeeResponse);
					
					return employeeResponse;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(employeeResponseList);

	}

	// create employee REST API

	@PostMapping("/employees")
	public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {

		LOGGER.info("---------------EmployeeController.createEmployee()");
		
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeRequest, employeeDto);

		EmployeeDto storedEmployeeDto = employeeService.createEmployee(employeeDto);
		
		EmployeeResponse employeeResponse = new EmployeeResponse();
		BeanUtils.copyProperties(storedEmployeeDto, employeeResponse);

		return ResponseEntity.ok(employeeResponse);
	}

	// get employee by email REST API
	
	@GetMapping("/employees/trigger/{email}")
	public ResponseEntity<EmployeeResponse> findEmployeeByEmail(@PathVariable String email) {

		LOGGER.info("---------------EmployeeController.findEmployeeByEmail()");

		EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email);
		EmployeeResponse employeeResponse = new EmployeeResponse();
		BeanUtils.copyProperties(employeeDto, employeeResponse);

		return ResponseEntity.ok(employeeResponse);
	}

	// get employee by id REST API
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

		LOGGER.info("---------------EmployeeController.getEmployeeById()");

		EmployeeDto employeeDto = employeeService.getEmployeeById(id);
		EmployeeResponse employeeResponse = new EmployeeResponse();
		BeanUtils.copyProperties(employeeDto, employeeResponse);

		return ResponseEntity.ok(employeeResponse);

	}

	// update employee REST API

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,
			@RequestBody EmployeeRequest employeeDetails) {

		LOGGER.info("---------------EmployeeController.updateEmployee()");

		EmployeeResponse updatedEmployee = new EmployeeResponse();
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeDetails, employeeDto);
		EmployeeDto createdEmployee = employeeService.updateEmployee(id, employeeDto);
		BeanUtils.copyProperties(createdEmployee, updatedEmployee);

		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee REST API
	
	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {

		LOGGER.info("---------------EmployeeController.deleteEmployee()");

		employeeService.deleteEmployee(id);
	}

}
