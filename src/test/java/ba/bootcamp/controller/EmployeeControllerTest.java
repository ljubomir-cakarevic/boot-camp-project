package ba.bootcamp.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.request.EmployeeRequest;
import ba.bootcamp.response.EmployeeResponse;
import ba.bootcamp.services.EmployeeServiceImpl;

class EmployeeControllerTest {
	
	@InjectMocks
	EmployeeController employeeController;
	
	@Mock
	EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testGetEmployeeById() {
		
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(1L);
		employeeDto.setFirstName("Bojan");
		employeeDto.setLastName("Starcevic");
		employeeDto.setAge(33);
		employeeDto.setEmail("test@test.com");
		employeeDto.setPosition("Developer");
		
		Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);
		
		ResponseEntity<EmployeeResponse> employeeResponse = employeeController.getEmployeeById(employeeDto.getId());
		
		assertNotNull(employeeResponse);
		assertEquals(employeeResponse.getStatusCode(), HttpStatus.OK);
		assertEquals(employeeResponse.getBody().getFirstName(), "Bojan");

	}

	@Test
	void testGetAllEmployees() {
		
		EmployeeDto employee1 = new EmployeeDto();
		employee1.setFirstName("Milan");
		employee1.setLastName("Popovic");
		
		EmployeeDto employee2 = new EmployeeDto();
		employee2.setFirstName("Goran");
		employee2.setLastName("Starcevic");
		
        List<EmployeeDto> employees = new ArrayList<EmployeeDto>();
        employees.add(employee1);
        employees.add(employee2);

		Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
		
		ResponseEntity<List<EmployeeResponse>> result = employeeController.getAllEmployees();
		
		assertNotNull(result);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertEquals(result.getBody().size(), 2);
	}
	
	@Test
	void testCreateEmployee() {
		
		EmployeeRequest employeeReq = new EmployeeRequest();

		employeeReq.setFirstName("Bojan");
		employeeReq.setLastName("Starcevic");
		employeeReq.setAge(33);
		employeeReq.setEmail("test@test.com");
		employeeReq.setPosition("Developer");
		
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeReq, employeeDto);
		
		
		Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(employeeDto);
		
		ResponseEntity<EmployeeResponse> response = employeeController.createEmployee(employeeReq);
		
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getLastName(), "Starcevic");
		assertEquals(response.getBody().getEmail(), "test@test.com");
	}
	
	@Test
	void testFindEmployeeByEmail() {
		
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(1L);
		employeeDto.setFirstName("Bojan");
		employeeDto.setLastName("Starcevic");
		employeeDto.setAge(33);
		employeeDto.setEmail("test@test.com");
		employeeDto.setPosition("Developer");
		
		Mockito.when(employeeService.getEmployeeByEmail("test@test.com")).thenReturn(employeeDto);
		
		ResponseEntity<EmployeeResponse> employeeResponse = employeeController.findEmployeeByEmail("test@test.com");
		
		assertNotNull(employeeResponse);
		assertEquals(employeeResponse.getStatusCode(), HttpStatus.OK);
		assertEquals(employeeResponse.getBody().getAge(), 33);
	}

}
