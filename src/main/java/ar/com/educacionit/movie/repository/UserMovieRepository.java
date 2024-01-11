package ar.com.educacionit.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.educacionit.movie.domain.Movie;
import ar.com.educacionit.movie.domain.User;

@Repository
public interface UserMovieRepository extends JpaRepository<User, Long>{

}
