package ba.bootcamp.services;


import java.util.List;

import ba.bootcamp.shared.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto employee);
	List<EmployeeDto> getAllEmployees();
	EmployeeDto getEmployeeById(Long id);
	EmployeeDto updateEmployee(Long id, EmployeeDto employee);
	void deleteEmployee(Long id);
	
	

}
