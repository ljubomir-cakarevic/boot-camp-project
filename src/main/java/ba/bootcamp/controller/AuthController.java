package ba.bootcamp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ba.bootcamp.entity.ERole;
import ba.bootcamp.entity.Role;
import ba.bootcamp.entity.User;
import ba.bootcamp.repository.RoleRepository;
import ba.bootcamp.repository.UserRepository;
import ba.bootcamp.request.LoginRequest;
import ba.bootcamp.request.SignupRequest;
import ba.bootcamp.response.JwtResponse;
import ba.bootcamp.response.MessageResponse;
import ba.bootcamp.security.jwt.JwtUtils;
import ba.bootcamp.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final static Logger LOGGER = Logger.getLogger(AuthController.class.getName());
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		LOGGER.info("------------------AuthController.authenticateUser()");
		
		// authenticate { username, pasword }
		// This call will invoke UserDetailsServiceImpl.loadUserByUsername(username) to build UserDetailsImpl object 
		// which contains all User Details.
		// Here we use Spring Security framework to check loginRequest parameters, 
		// compare them with loaded user from database
		// and if they match, it will return Authentication object.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		
		
		// On authenticationManager.authenticate() method call we are using Spring Security framework to get Authentication.
		// Framework requires UserDetailsService implementation (UserDetailsServiceImpl) to load user by username and return UserDetails
		// implementation which contains all necessary user parameters.
		// UserDetailsImpl object is stored in Authentication.getPrincipal()
		// Now when it is loaded we can use all data to build JwsResponse object which contains all data we need to use
		// JWT in frontend part.
		
		
		
		// update SecurityContext using Authentication object
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// generate JWT
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		// get UserDetails from Authentication object
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		// response contains JWT and UserDetails data
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles)); //userDetails.getRoles()));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		LOGGER.info("------------------AuthController.registerUser()");
		// check existing username
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		// check existing email
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account (with ROLE_USER if not specifying role)
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));


		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		// save user to database using UserRepository
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
