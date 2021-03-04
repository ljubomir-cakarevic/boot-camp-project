package ba.bootcamp.services;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.entity.Employee;
import ba.bootcamp.repository.EmployeeRepository;

class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeService;

	@Mock
	EmployeeRepository employeeRepo;
	
	@Mock
	private ModelMapper modelMapper;
	
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
		
		Employee employee = new Employee("Marko", "Panic", 36, "test@test.com", "Manager");
		String email = "test@test.com";
		
		when(employeeRepo.findByEmail(email)).thenReturn(employee);
		
		EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email);
		
		assertEquals(employee.getPosition(), employeeDto.getPosition());
		
	}
	
	@Test
	void testCreateEmployee() {
	
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(1L);
		employeeDto.setFirstName("Boris");
		employeeDto.setLastName("Panic");
		employeeDto.setAge(34);
		employeeDto.setEmail("boris@panic.com");
		employeeDto.setPosition("Developer");
		
		Employee employeeEntity = new Employee();
		employeeEntity.setId(1L);
		employeeEntity.setFirstName("Boris");
		employeeEntity.setLastName("Panic");
		employeeEntity.setAge(34);
		employeeEntity.setEmail("boris@panic.com");
		employeeEntity.setPosition("Developer");

		when(employeeRepo.save(employeeEntity)).thenReturn(employeeEntity);	
		
		employeeService.createEmployee(employeeDto);
		
		assertEquals(employeeEntity.getFirstName(), employeeDto.getFirstName());
		assertEquals(employeeEntity.getLastName(), employeeDto.getLastName());
		assertEquals(employeeEntity.getAge(), employeeDto.getAge());
		
	}
	

}
