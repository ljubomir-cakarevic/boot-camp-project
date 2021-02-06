package ba.bootcamp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.bootcamp.dto.EmployeeDto;
import ba.bootcamp.entity.Employee;
import ba.bootcamp.exception.ResourceNotFoundException;
import ba.bootcamp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;

	@Override
	@Transactional
	public EmployeeDto createEmployee(EmployeeDto employee) {
		
		Employee employeeEntity = new Employee();
		BeanUtils.copyProperties(employee, employeeEntity);
		
		Employee storedEmployee = employeeRepo.save(employeeEntity);
		
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(storedEmployee, employeeDto);
		
		return employeeDto;
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employeeList = employeeRepo.findAll();

		List<EmployeeDto> employeeDtoList = employeeList.stream()
				.map(e -> {
					EmployeeDto employeeDto = new EmployeeDto();
					BeanUtils.copyProperties(e, employeeDto);
					
					return employeeDto;
				})
				.collect(Collectors.toList());
		
		//		for (Employee employee : employeeList) {
//			EmployeeDto employeeDto = new EmployeeDto();
//			BeanUtils.copyProperties(employee, employeeDto);
//			employeeDtoList.add(employeeDto);
//		}

		return employeeDtoList;
	}
	
	@Override
	public EmployeeDto getEmployeeById(Long id) {

		Employee employeeEntity = employeeRepo.findById(id).get();
		
		EmployeeDto employeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeEntity, employeDto);

		return employeDto;
	}

	@Override
	@Transactional
	public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {

		Employee employeeEntity = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());
		employeeEntity.setAge(employee.getAge());
		employeeEntity.setEmail(employee.getEmail());
		employeeEntity.setPosition(employee.getPosition());

		Employee updateEmployee = employeeRepo.save(employeeEntity);
		BeanUtils.copyProperties(updateEmployee, employee);

		return employee;
	}

	@Override
	@Transactional
	public void deleteEmployee(Long id) {

		Employee employeeEntity = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Record not found: " + id));

		employeeRepo.delete(employeeEntity);

	}

	@Override
	public EmployeeDto getEmployeeByEmail(String email) {
		
		Employee employeeEntity = employeeRepo.findByEmail(email);
		
		EmployeeDto employeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeEntity, employeDto);
		
		return employeDto;
	}

}
