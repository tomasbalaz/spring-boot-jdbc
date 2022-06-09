package com.amigoscode.movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> selectMovies() {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                LIMIT 100;
                """;
        List<Movie> movies = jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Movie(
                    rs.getInt("id"),
                    rs.getString("name"),
                    null,
                    LocalDate.parse(rs.getString("release_date"))
            );
        });
        return movies;
    }

    @Override
    public int insertMovie(Movie movie) {
        String sql = """
            INSERT INTO movie(name, release_date) VALUES(?,?);
        """;
        return jdbcTemplate.update(sql, movie.name(), movie.releaseDate());
    }

    @Override
    public int deleteMovie(int id) {
        throw new UnsupportedOperationException("not implemented");

    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        throw new UnsupportedOperationException("not implemented");
    }
    
}
