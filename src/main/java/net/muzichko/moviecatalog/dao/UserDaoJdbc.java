package net.muzichko.moviecatalog.dao;


import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDaoJdbc implements UserDao {

    static Logger log = Logger.getLogger(UserDaoJdbc.class);

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
    public void add(User user) throws CantAddEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Adding user " + user.toString());

        checkConnection();

        String query = "insert into users(login, password, email) values(?,?,?); ";

        String baseErrorMessage = "User wasn't added in DB " + user.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            int ok = statement.executeUpdate();
            if (ok == 0) {
                String errorMessage = baseErrorMessage + "statement.executeUpdate(" + query + ") returns 0.";
                log.error(errorMessage);
                throw new CantAddEntityException(errorMessage);
            } else {
                fillNewIdFromDB(user);
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

        log.info("Getting list of users.");

        checkConnection();

        List<MovieCatalogEntity> userList = new LinkedList<>();

        String query = "select * from users order by login;";

        String baseErrorMessage = "Couldn't get list of users. ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                User user = new User(resultSet.getInt("id"),
                        resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("email"));

                userList.add(user);
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
        return userList;
    }

    @Override
    public User getById(int id) throws NoSuchEntityException, CantGetEntityListException, CantGetDBConnection {  // TODO: exceptions ???

        log.info("Getting user by id " + id);

        checkConnection();

        String query = "select * from users where id = ?;";

        String baseErrorMessage = "Couldn't get user by id = " + id + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getInt("id"),
                            resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("email"));
                } else {
                    String errorMessage = baseErrorMessage + "No such user in DB.";
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
    public void update(User user) throws CantUpdateEntityException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Updating user " + user.toString());

        checkConnection();

        String query = "update users set login = ?, password = ?, email = ? where id = ?;";

        String baseErrorMessage = "Couldn't update user: " + user.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());
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
    public void delete(User user) throws CantDeleteEntityException, CantGetDBConnection {

        log.info("Deleting user " + user.toString());

        checkConnection();

        String query = "delete from users where id = ?;";

        String baseErrorMessage = "Couldn't delete user: " + user.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, user.getId());
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
            throw new CantDeleteEntityException(baseErrorMessage + query, e);
        }
    }

    @Override
    public void alreadyExists(User user) throws EntityAlreadyExistsException, UnablePerformDBOperation, CantGetDBConnection {

        log.info("Searching user " + user.toString() + " in DB");

        checkConnection();

        String queryLogin = "select * from users where login = ? ";
        String queryEmail = "select * from users where email = ? ";

        boolean updatingUser = (user.getId() != 0);
        if (updatingUser) {
            queryLogin += "and id != ?";
            queryEmail += "and id != ?";
        }

        String baseErrorMessage = "Exception while checking user in DB: " + user.toString() + ". ";

        try {

            try (PreparedStatement statement = connection.prepareStatement(queryLogin)) {
                statement.setString(1, user.getLogin());
                if (updatingUser) {
                    statement.setInt(2, user.getId());
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String errorMessage = "User with login " + user.getLogin() + " already alreadyExists in DB";
                        log.error(errorMessage);
                        throw new EntityAlreadyExistsException(errorMessage);
                    }
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(queryEmail)) {
                statement.setString(1, user.getEmail());
                if (updatingUser) {
                    statement.setInt(2, user.getId());
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String errorMessage = "User with email " + user.getEmail() + " already alreadyExists in DB";
                        log.error(errorMessage);
                        throw new EntityAlreadyExistsException(errorMessage);
                    }
                }
            }

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage);
            throw new UnablePerformDBOperation(baseErrorMessage, e);
        }
    }

    @Override
    public void fillNewIdFromDB(User user) throws UnablePerformDBOperation, CantGetDBConnection {

        log.info("Filling the new id in the user " + user.toString());

        checkConnection();

        String query = "select id from users where login = ?;";

        String baseErrorMessage = "New id wasn't filled in the user " + user.toString() + ". ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getLogin());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }
}
