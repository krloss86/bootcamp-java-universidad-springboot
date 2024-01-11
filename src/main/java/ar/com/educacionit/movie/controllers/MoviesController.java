package ar.com.educacionit.movie.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.educacionit.movie.domain.Movie;
import ar.com.educacionit.movie.domain.MovieGenre;
import ar.com.educacionit.movie.dto.MovieRequestDTO;
import ar.com.educacionit.movie.services.MovieService;

@RestController
@RequestMapping("/movie")
public class MoviesController {
	
	private final MovieService movieService;
	
	public MoviesController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@GetMapping
	public List<Movie> findAllMovies() {
		/*List<Movie> pelis = repository.findAll();
		for(Movie x : pelis) {
			System.out.println("inicializando proxy");
			Hibernate.initialize(x.getGenreIds());
		}
		return pelis;
		*/
		
		return movieService.obtenerListado();
	}
	
	//una pelicula dada si id 
	@GetMapping("/{id}") 
	public ResponseEntity<Movie> buscarPorId(
			@PathVariable("id") Long id
		) {
		
		Movie movie = movieService.obtenerPorId(id);
		
		if(movie == null) {
			return ResponseEntity.notFound().build();			
		}
		
		return ResponseEntity.ok(movieService.obtenerPorId(id));
	}
	
	//crear una pelicula
	@PostMapping
	public ResponseEntity<Long> crearMovie (
				@RequestBody MovieRequestDTO request
			) {
		
		//el post no es idempotente
		
		Optional<Movie> movie = this.movieService.buscarPorTitulo(request.getOriginalTitle());
		if(movie.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(movie.get().getId());
		}
		
		//lo crea y luego lo retorna
		Movie newMovie = new Movie();
		newMovie.setAdult(request.getAdult());
		newMovie.setBackdropPath(request.getBackdropPath());
		
		List<MovieGenre> genres = request.getGenreIds()
			.stream()
			.map(x -> new MovieGenre(x))
			.collect(Collectors.toList());
		newMovie.setGenreIds(genres);
		
		newMovie.setOriginalLanguage(request.getOriginalLanguage());
		newMovie.setOriginalTitle(request.getOriginalTitle());
		newMovie.setOverview(request.getOverview());
		newMovie.setPopularity(request.getPopularity());
		newMovie.setPosterPath(request.getPosterPath());
		newMovie.setReleaseDate(request.getReleaseDate());
		newMovie.setTitle(request.getTitle());
		newMovie.setVideo(request.getVideo());
		newMovie.setVoteAverage(request.getVoteAverage());
		
		newMovie.setVoteCount(request.getVoteCount());
		
		this.movieService.crearMovie(newMovie);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newMovie.getId());
	}
	
	
	//eliminar una peli dado si id 
	@DeleteMapping("/{id}") 
	public ResponseEntity<Void> eliminarPorId(
			@PathVariable("id") Long id
		) {
		
		try {
			movieService.eliminarPorId(id);
		
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	//actualizar una peli
}
