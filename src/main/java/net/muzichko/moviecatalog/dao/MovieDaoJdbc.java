package net.muzichko.moviecatalog.dao;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MovieDaoJdbc implements MovieDao {

    static Logger log = Logger.getLogger(MovieDaoJdbc.class);

    private Connection connection;

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    private void checkConnection() throws CantGetDBConnection {

        if(this.connection == null){
            log.error("No connection to DB.");
            throw new CantGetDBConnection("No connection to DB.");
        }
    }

    @Override
    public void add(Movie movie) throws CantAddEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Adding movie " + movie.toString());

        checkConnection();

        String query;
        
        if (movie.getId() > 0) {
        	query = "insert into movies(id, name, idgenre, idcountry, description, starring, year)" +
                    " values(?, ?, ?, ?, ?, ?, ?); ";
        	
        }else {
        	query = "insert into movies(name, idgenre, idcountry, description, starring, year)" +
                    " values(?, ?, ?, ?, ?, ?); ";
        }
        
        String baseErrorMessage = "Movie wasn't added in DB " + movie.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	
        	if (movie.getId() > 0) {
        		statement.setInt(1, movie.getId());
        		statement.setString(2, movie.getName());
        		statement.setInt(3,movie.getGenre().getId());
        		statement.setInt(4, movie.getCountry().getId());
        		statement.setString(5, movie.getDescription());
        		statement.setString(6, movie.getStarring());
        		statement.setInt(7, movie.getYear());
        	}else {
        		statement.setString(1, movie.getName());
        		statement.setInt(2,movie.getGenre().getId());
        		statement.setInt(3, movie.getCountry().getId());
        		statement.setString(4, movie.getDescription());
        		statement.setString(5, movie.getStarring());
        		statement.setInt(6, movie.getYear());
        	}
        	
            //fillStatementArgs(movie, statement);
            connection.commit();
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + " statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantAddEntityException(errorMessage);
            } else {
                fillNewIdFromDB(movie);
            }
        } catch (CantAddEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public List<MovieCatalogEntity> list() throws CantGetEntityListException, CantGetDBConnection {

        log.info("Getting list of movies");

        checkConnection();

        List<MovieCatalogEntity> movieList = new LinkedList<>();

        String query = "select m.*, g.name as genre, c.name as country " +
                " from movies as m left join genres as g on m.idgenre = g.id " +
                " left join countries as c on m.idcountry = c.id " +
                " order by genre, name;";

        String baseErrorMessage = "Couldn't get list of movies. ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Movie movie = movieFromResultSet(resultSet);
                movieList.add(movie);
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return movieList;
    }

    @Override
    public List<MovieCatalogEntity> listByYear(int year) throws CantGetEntityListException, CantGetDBConnection {

        log.info("Getting list of movies by year = " + year);

        checkConnection();

        List<MovieCatalogEntity> movieList = new LinkedList<>();

        String query = "select m.*, g.name as genre, c.name as country " +
                " from movies as m left join genres as g on m.idgenre = g.id " +
                " left join countries as c on m.idcountry = c.id where m.year = ? order by m.name";

        String baseErrorMessage = "Couldn't get list of movies by year: " + year + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, year);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie = movieFromResultSet(resultSet);
                    movieList.add(movie);
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return movieList;
    }

    @Override
    public List<MovieCatalogEntity> listByGenre(Genre genre) throws CantGetDBConnection, CantGetEntityListException {

        log.info("Getting list of movies by genre " + genre.toString());

        checkConnection();

        List<MovieCatalogEntity> movieList = new LinkedList<>();

        String query = "select m.*, g.name as genre, c.name as country " +
                " from movies as m left join genres as g on m.idgenre = g.id " +
                " left join countries as c on m.idcountry = c.id where m.idgenre = ? order by m.name";

        String baseErrorMessage = "Couldn't get list of movies by genre: " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, genre.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie = movieFromResultSet(resultSet);
                    movieList.add(movie);
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return movieList;
    }

    @Override
    public List<MovieCatalogEntity> listByCountry(Country country) throws CantGetDBConnection, CantGetEntityListException {

        log.info("Getting list of movies by country " + country.toString());

        checkConnection();

        List<MovieCatalogEntity> movieList = new LinkedList<>();

        String query = "select m.*, g.name as genre, c.name as country " +
                " from movies as m left join genres as g on m.idgenre = g.id " +
                " left join countries as c on m.idcountry = c.id where m.idcountry = ? order by name";

        String baseErrorMessage = "Couldn't get list of movies by country: " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, country.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie = movieFromResultSet(resultSet);
                    movieList.add(movie);
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return movieList;
    }

    @Override
    public Movie getById(int id) throws NoSuchEntityException, CantGetEntityListException, CantGetDBConnection {

        log.info("Getting movie by id = " + id);

        checkConnection();

        String query = "select m.*, g.name as genre, c.name as country " +
                " from movies as m left join genres as g on m.idgenre = g.id " +
                " left join countries as c on m.idcountry = c.id " +
                " where m.id = ?;";

        String baseErrorMessage = "Couldn't get movie by id = " + id + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return movieFromResultSet(resultSet);
                } else {
                    return null; /*
                	String errorMessage = baseErrorMessage + "No such movie in DB.";
                    log.error(errorMessage);
                    throw new NoSuchEntityException(errorMessage);
                    */
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
    }

    @Override
    public void update(Movie movie) throws CantGetDBConnection, CantUpdateEntityException, UnablePerformDBOperation {

        log.info("Updating movie " + movie.toString());

        checkConnection();

        String query = "update movies " +
                " set name = ?, idgenre = ?, idcountry = ?, description = ?, starring = ?, year = ? where id = ?;";

        String baseErrorMessage = "Couldn't update movie: " + movie.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            fillStatementArgs(movie, statement);
            statement.setInt(7, movie.getId());
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + "statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantUpdateEntityException(errorMessage);
            }
        } catch (CantUpdateEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    private void fillStatementArgs(Movie movie, PreparedStatement statement) throws SQLException {
        statement.setString(1, movie.getName());
        statement.setInt(2, movie.getGenre().getId());
        statement.setInt(3, movie.getCountry().getId());
        statement.setString(4, movie.getDescription());
        statement.setString(5, movie.getStarring());
        statement.setInt(6, movie.getYear());
    }

    @Override
    public void delete(Movie movie) throws CantDeleteEntityException, CantGetDBConnection, UnablePerformDBOperation {

        log.info("Deleting movie " + movie.toString());

        checkConnection();

        String query = "delete from movies where id = ?;";

        String baseErrorMessage = "Couldn't delete movie: " + movie.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, movie.getId());
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + "statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantDeleteEntityException(errorMessage);
            }
        } catch (CantDeleteEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public void alreadyExists(Movie movie) throws CantGetDBConnection, EntityAlreadyExistsException, UnablePerformDBOperation {

        log.info("Searching movie " + movie.toString() + " in DB");

        checkConnection();

        String query = "select * from movies ";

        boolean updatingMovie = (movie.getId() != 0);
        if (updatingMovie) {
            query += " where name = ? and year = ? and id != ?";
        } else {
            query += " where name = ? and year = ?";
        }

        String baseErrorMessage = "Exception while checking movie in DB: " + movie.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getYear());
            if (updatingMovie) {
                statement.setInt(3, movie.getId());
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String errorMessage = baseErrorMessage + "Movie with name " + movie.getName() +
                            " and year " + movie.getYear() + " already alreadyExists in DB";
                    log.error(errorMessage);
                    throw new EntityAlreadyExistsException(errorMessage);
                }
            }
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public void fillNewIdFromDB(Movie movie) throws UnablePerformDBOperation, CantGetDBConnection {

        log.info("Filling the new id in the movie " + movie.toString());

        checkConnection();

        String query = "select id from movies where name = ? and year = ?;";

        String baseErrorMessage = "New id wasn't filled in the movie " + movie.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getYear());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    movie.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    private Movie movieFromResultSet(ResultSet resultSet) throws SQLException {

        Genre genre = new Genre(resultSet.getInt("idgenre"), resultSet.getString("genre"));
        Country country = new Country(resultSet.getInt("idcountry"), resultSet.getString("country"));

        return new Movie(resultSet.getInt("id"),
                resultSet.getString("name"),
                genre,
                resultSet.getString("description"),
                resultSet.getString("starring"),
                resultSet.getInt("year"),
                country);
    }
}
