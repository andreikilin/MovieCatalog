package net.muzichko.moviecatalog.dao;


import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Repository
public class GenreDaoJbdc implements GenreDao {

    static Logger log = Logger.getLogger(GenreDaoJbdc.class);

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
    public void add(Genre genre) throws CantAddEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Adding genre " + genre.toString());

        checkConnection();

        String query = "insert into genres(name) values(?); ";

        String baseErrorMessage = "Genre wasn't added in DB " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genre.getName());
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + "statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantAddEntityException(errorMessage);
            } else {
                fillNewIdFromDB(genre);
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

        log.info("Getting list of genres.");

        checkConnection();

        List<MovieCatalogEntity> genreList = new LinkedList<>();

        String query = "select * from genres order by name;";

        String baseErrorMessage = "Couldn't get list of genres. ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                Genre genre = new Genre(resultSet.getInt("id"),
                        resultSet.getString("name"));

                genreList.add(genre);
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return genreList;
    }

    @Override
    public Genre getById(int id) throws NoSuchEntityException, CantGetEntityListException, CantGetDBConnection {

        log.info("Getting genre by id = " + id);

        checkConnection();

        String query = "select * from genres where id = ?;";

        String baseErrorMessage = "Couldn't get genre by id = " + id + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre(resultSet.getInt("id"),
                            resultSet.getString("name"));
                } else {
                    String errorMessage = baseErrorMessage + "No such genre in DB.";
                    log.error(errorMessage);
                    throw new NoSuchEntityException(errorMessage);
                }
            }
        } catch (NoSuchEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
    }

    @Override
    public void update(Genre genre) throws CantUpdateEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Updating genre " + genre.toString());

        checkConnection();

        String query = "update genres set name = ? where id = ?;";

        String baseErrorMessage = "Couldn't update genre: " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genre.getName());
            statement.setInt(2, genre.getId());
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

    @Override
    public void delete(Genre genre) throws CantDeleteEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Deleting genre " + genre.toString());

        checkConnection();

        String query = "delete from genres where id = ?;";

        String baseErrorMessage = "Couldn't delete genre: " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, genre.getId());
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
    public void alreadyExists(Genre genre) throws EntityAlreadyExistsException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Searching genre " + genre.toString() + " in DB");

        checkConnection();

        String query = "select * from genres ";

        boolean updatingGenre = (genre.getId() != 0);
        if (updatingGenre) {
            query += " where name = ? and id != ?";
        } else {
            query += " where name = ?";
        }

        String baseErrorMessage = "Exception while checking genre in DB: " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genre.getName());
            if (updatingGenre) {
                statement.setInt(2, genre.getId());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String errorMessage = "Genre with name " + genre.getName() + " already alreadyExists in DB";
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
    public void fillNewIdFromDB(Genre genre) throws UnablePerformDBOperation, CantGetDBConnection {

        log.info("Filling the new id in the genre " + genre.toString());

        checkConnection();

        String query = "select id from genres where name = ?;";

        String baseErrorMessage = "New id wasn't filled in the genre " + genre.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genre.getName());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    genre.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }
}
