package ba.bootcamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ba.bootcamp.entity.ERole;
import ba.bootcamp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(ERole name);
}
