package com.amigoscode.movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.query(sql, new MovieRowMapper());
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
        var sql = """
                DELETE FROM movie
                WHERE id = ?
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE id = ?;
                """;
        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper(), id);
        return movies.stream()
                .findFirst();
    }

    @Override
    public Optional<Movie> selectMovieByName(String name) {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE name = ?
                """;
        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper(), name);
        return  movies.stream()
                .filter(movie -> name.equalsIgnoreCase(movie.name()))
                .findFirst();
    }

    @Override
    public int updateMovie(int id, Movie movie) {
        var sql = """
                  UPDATE movie
                  SET name = ?, release_date = ?
                  WHERE id = ?
                  """;
        return jdbcTemplate.update(sql, movie.name(), movie.releaseDate(), id);
    }
}
