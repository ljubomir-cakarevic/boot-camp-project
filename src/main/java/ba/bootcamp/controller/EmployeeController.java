package ba.bootcamp.controller;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
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

import ba.bootcamp.request.EmployeeRequest;
import ba.bootcamp.response.EmployeeResponse;
import ba.bootcamp.services.EmployeeService;
import ba.bootcamp.shared.dto.EmployeeDto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	private final static Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	// get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

		LOGGER.info("---------------EmployeeController.getAllEmployees()");

		List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
		List<EmployeeResponse> employeeResponseList = new ArrayList<EmployeeResponse>();

		for (EmployeeDto employeeDto : employeeDtoList) {
			EmployeeResponse employeeResponse = new EmployeeResponse();
			BeanUtils.copyProperties(employeeDto, employeeResponse);
			employeeResponseList.add(employeeResponse);
		}

		return ResponseEntity.ok(employeeResponseList);

	}

	// create employee rest api

	@PostMapping("/employees")
	public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {

		LOGGER.info("---------------EmployeeController.createEmployee()");

		EmployeeDto employeeDto = modelMapper.map(employeeRequest, EmployeeDto.class);
		EmployeeDto storedEmployeeDto = employeeService.createEmployee(employeeDto);
		EmployeeResponse employeeResponse = modelMapper.map(storedEmployeeDto, EmployeeResponse.class);

		return ResponseEntity.ok(employeeResponse);
	}

	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

		LOGGER.info("---------------EmployeeController.getEmployeeById()");

		EmployeeDto employeeDto = employeeService.getEmployeeById(id);
		EmployeeResponse employeeResponse = new EmployeeResponse();
		BeanUtils.copyProperties(employeeDto, employeeResponse);

		return ResponseEntity.ok(employeeResponse);

	}

	// update employee rest api

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeDetails) {
		
		LOGGER.info("---------------EmployeeController.updateEmployee()");
		
		EmployeeResponse updatedEmployee = new EmployeeResponse();
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeDetails, employeeDto);
		EmployeeDto createdEmployee = employeeService.updateEmployee(id, employeeDto);
		BeanUtils.copyProperties(createdEmployee, updatedEmployee);
		
		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {

		LOGGER.info("---------------EmployeeController.deleteEmployee()");

		employeeService.deleteEmployee(id);
	}

}
