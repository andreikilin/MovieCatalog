package net.muzichko.moviecatalog.dao;

import net.muzichko.moviecatalog.domain.Country;
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
public class CountryDaoJdbc implements CountryDao {

    static Logger log = Logger.getLogger(CountryDaoJdbc.class);

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
    public void add(Country country) throws CantAddEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Adding country " + country.toString());

        checkConnection();

        String query = "insert into countries(name) values(?); ";

        String baseErrorMessage = "Country wasn't added in DB " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, country.getName());
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + "statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantAddEntityException(errorMessage);
            } else {
                fillNewIdFromDB(country);
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

        log.info("Getting list of movies.");

        checkConnection();

        List<MovieCatalogEntity> countryList = new LinkedList<>();

        String query = "select * from countries order by name;";

        String baseErrorMessage = "Couldn't get list of countries. ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Country country = new Country(resultSet.getInt("id"),
                        resultSet.getString("name"));

                countryList.add(country);
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return countryList;
    }

    @Override
    public Country getById(int id) throws CantGetEntityListException, NoSuchEntityException, CantGetDBConnection {

        log.info("Getting movie by id = " + id);

        checkConnection();

        String query = "select * from countries where id = ?;";

        String baseErrorMessage = "Couldn't get country by id = " + id + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return new Country(resultSet.getInt("id"),
                            resultSet.getString("name"));
                } else {
                    String errorMessage = baseErrorMessage + "No such country in DB.";
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
    public void update(Country country) throws CantUpdateEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Updating country " + country.toString());

        checkConnection();

        String query = "update countries " +
                " set name = ? where id = ?;";

        String baseErrorMessage = "Couldn't update country: " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, country.getName());
            statement.setInt(2, country.getId());
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
    public void delete(Country country) throws CantDeleteEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Deleting country " + country.toString());

        checkConnection();

        String query = "delete from countries " +
                " where id = ?;";

        String baseErrorMessage = "Couldn't delete country: " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, country.getId());
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
    public void alreadyExists(Country country) throws EntityAlreadyExistsException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Searching country " + country.toString() + " in DB");

        checkConnection();

        String query = "select * from countries ";

        boolean updatingCountry = (country.getId() != 0);
        if (updatingCountry) {
            query += " where name = ? and id != ?";
        } else {
            query += " where name = ?";
        }

        String baseErrorMessage = "Exception while checking existing country in DB: " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, country.getName());
            if(updatingCountry){
                statement.setInt(2, country.getId());
            }

            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    String errorMessage = "Country with name " + country.getName() + " already alreadyExists in DB";
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
    public void fillNewIdFromDB(Country country) throws UnablePerformDBOperation, CantGetDBConnection {

        log.info("Filling the new id in the country " + country.toString());

        checkConnection();

        String query = "select id from countries where name = ?";

        String baseErrorMessage = "New id wasn't filled in the country " + country.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, country.getName());
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    country.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }
}
