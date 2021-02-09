package ba.bootcamp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.entity.Employee;
import ba.bootcamp.repository.EmployeeRepository;

class EmployeeServiceImplTest {

	@InjectMocks // @InjectMocks also creates the mock implementation, additionally injects the dependent mocks that are marked with the annotations @Mock into it
	EmployeeServiceImpl employeeService;

	@Mock // The @Mock annotation creates a mock implementation for the class it is annotated with
	EmployeeRepository employeeRepo;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetEmployeeById() {
		
		Employee employeeEntity = new Employee();
		employeeEntity.setId(1L);
		employeeEntity.setFirstName("Milos");
		employeeEntity.setLastName("Ilic");
		employeeEntity.setEmail("test@test.com");

		when(employeeRepo.findById(1L)).thenReturn(Optional.of(employeeEntity));

		EmployeeDto employeeDto = employeeService.getEmployeeById(1L);

		assertNotNull(employeeDto);
		assertEquals("Milos", employeeDto.getFirstName());
		assertEquals("Ilic", employeeDto.getLastName());
	}
	
	@Test
	void testGetAllEmployees() {
		
		List<Employee> data = new ArrayList<Employee>();
		
		data.add(new Employee("Milan", "Peric", 34, "milan@test.com", "Developer"));
		data.add(new Employee("Dejan", "Simic", 47, "dejan@test.com", "Manager"));
		data.add(new Employee("Slavko", "Jovic", 32, "slavko@test.com", "Developer"));
		
		when(employeeRepo.findAll()).thenReturn(data);
		
		List<EmployeeDto> employeesListDto = employeeService.getAllEmployees();
		assertEquals(3, employeesListDto.size());
	
	}
	
	@Test
	void testGetEmployeeByEmail() {
		
		String email = "test@test.com";
		String position = "Manager";
		Employee employee = new Employee("Marko", "Panic", 36, email, position);
		
		when(employeeRepo.findByEmail(email)).thenReturn(employee);
		
		EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email);
		
		assertNotNull(employeeDto);
		assertEquals(position, employeeDto.getPosition());
		
	}
	
	@Test
	void testCreateEmployee() {
		
		Employee employeeEntity = new Employee();
		employeeEntity.setId(1L);
		employeeEntity.setFirstName("Milos");
		employeeEntity.setLastName("Ilic");
		employeeEntity.setAge(30);
		employeeEntity.setEmail("test@test.com");
		employeeEntity.setPosition("Developer");
		
		when(employeeRepo.save(Mockito.any(Employee.class))).thenReturn(employeeEntity);
		
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeEntity, employeeDto);
		
		EmployeeDto storedEmployeeDto = employeeService.createEmployee(employeeDto);
		
		assertEquals(employeeEntity.getFirstName(), storedEmployeeDto.getFirstName());
	}
	
	

}
