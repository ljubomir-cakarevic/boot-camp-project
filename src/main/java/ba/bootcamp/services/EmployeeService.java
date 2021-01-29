package ba.bootcamp.services;


import java.util.List;

import ba.bootcamp.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto employee);
	List<EmployeeDto> getAllEmployees();
	EmployeeDto getEmployeeByEmail(String emailId);
	EmployeeDto getEmployeeById(Long id);
	EmployeeDto updateEmployee(Long id, EmployeeDto employee);
	void deleteEmployee(Long id);
	
	

}
