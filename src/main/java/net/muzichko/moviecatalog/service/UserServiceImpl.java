package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.dao.UserDao;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.domain.UserValidator;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static net.muzichko.moviecatalog.service.Utils.getConnection;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public void add(User user) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException {

        Connection connection = getConnection(dataSource, log);
        userDao.setConnection(connection);

        try{

            userDao.alreadyExists(user);
            UserValidator.validate(user);
            userDao.add(user);

            connection.commit();

        } catch(Exception e){
            try {
                connection.rollback();
                throw e;
            } catch (SQLException eRollback) {
                log.error(eRollback.getMessage());
                throw new UnablePerformDBOperation(eRollback);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't return connection to pool. " + e.getMessage());
                throw new UnablePerformDBOperation("Can't return connection to pool. " + e.getMessage(), e);
            }
        }
    }

    @Override
    public List<MovieCatalogEntity> list() throws MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        userDao.setConnection(connection);

        try{

            return userDao.list();

        } catch(Exception e){
            throw e;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't return connection to pool. " + e.getMessage());
                throw new UnablePerformDBOperation("Can't return connection to pool. " + e.getMessage(), e);
            }
        }
    }

    @Override
    public User getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        userDao.setConnection(connection);

        try{

            return userDao.getById(id);

        } catch(Exception e){
            throw e;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't return connection to pool. " + e.getMessage());
                throw new UnablePerformDBOperation("Can't return connection to pool. " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void update(User user) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException {

        Connection connection = getConnection(dataSource, log);
        userDao.setConnection(connection);

        try{

            userDao.alreadyExists(user);
            UserValidator.validate(user);
            userDao.update(user);

            connection.commit();

        } catch(Exception e){
            try {
                connection.rollback();
                throw e;
            } catch (SQLException e1) {
                log.error(e1.getMessage());
                throw new UnablePerformDBOperation(e1);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't return connection to pool. " + e.getMessage());
                throw new UnablePerformDBOperation("Can't return connection to pool. " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void delete(User user) throws CantDeleteEntityException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        userDao.setConnection(connection);

        try{

            userDao.delete(user);

            connection.commit();

        } catch(Exception e){
            try {
                connection.rollback();
                throw e;
            } catch (SQLException e1) {
                log.error(e1.getMessage());
                throw new UnablePerformDBOperation(e1);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Can't return connection to pool. " + e.getMessage());
                throw new UnablePerformDBOperation("Can't return connection to pool. " + e.getMessage(), e);
            }
        }
    }
}
