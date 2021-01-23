package ba.bootcamp.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.bootcamp.entity.Employee;
import ba.bootcamp.exception.ResourceNotFoundException;
import ba.bootcamp.repository.EmployeeRepository;
import ba.bootcamp.shared.dto.EmployeeDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmployeeDto createEmployee(EmployeeDto employee) {

		// ModelMapper modelMapper = new ModelMapper();
		Employee employeeEntity = modelMapper.map(employee, Employee.class);
		Employee storedEmployee = employeeRepo.save(employeeEntity);
		EmployeeDto returnValue = modelMapper.map(storedEmployee, EmployeeDto.class);

		return returnValue;
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employeeList = employeeRepo.findAll();

		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();

		for (Employee employee : employeeList) {
			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(employee, employeeDto);
			employeeDtoList.add(employeeDto);
		}

		return employeeDtoList;
	}

	@Override
	public EmployeeDto getEmployeeById(Long id) {

		Employee employeeEntity = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		EmployeeDto employeDto = new EmployeeDto();
		BeanUtils.copyProperties(employeeEntity, employeDto);

		return employeDto;
	}

	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {

		Employee employeeEntity = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());
		employeeEntity.setEmailId(employee.getEmailId());

		Employee updateEmployee = employeeRepo.save(employeeEntity);
		BeanUtils.copyProperties(updateEmployee, employee);

		return employee;
	}

	@Override
	public void deleteEmployee(Long id) {

		Employee employeeEntity = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Record not found: " + id));

		employeeRepo.delete(employeeEntity);

	}

}
