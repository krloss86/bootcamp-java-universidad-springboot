package ar.com.educacionit.movie.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.educacionit.movie.domain.User;
import ar.com.educacionit.movie.repository.UserMovieRepository;

@Service
public class UserService {
	
	//@Autowired
	//private UserMovieRepository userRepository;
	private final UserMovieRepository userRepository;
	
	public UserService(UserMovieRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> obtenerListado() {
		return this.userRepository.findAll();
	}
}
