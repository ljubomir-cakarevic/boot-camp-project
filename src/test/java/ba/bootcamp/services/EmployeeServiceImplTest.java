package ba.bootcamp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.entity.Employee;
import ba.bootcamp.repository.EmployeeRepository;

class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeService;

	@Mock
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

}
