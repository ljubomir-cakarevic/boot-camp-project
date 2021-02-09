package ba.bootcamp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ba.bootcamp.entity.Employee;

import java.lang.String;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByEmail(String email);
	
	

}
