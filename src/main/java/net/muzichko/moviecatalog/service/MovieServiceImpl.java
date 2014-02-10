package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.dao.MovieDao;
import net.muzichko.moviecatalog.domain.*;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static net.muzichko.moviecatalog.service.Utils.getConnection;

@Service("MovieService")
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private DataSource dataSource;

    static Logger log = Logger.getLogger(MovieServiceImpl.class);

    @Override
    public void add(Movie movie) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            movieDao.alreadyExists(movie);
            MovieValidator.validate(movie);
            movieDao.add(movie);

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
        movieDao.setConnection(connection);

        try{

            return movieDao.list();

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
    public List<MovieCatalogEntity> listByYear(int year) throws MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            return movieDao.listByYear(year);

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
    public List<MovieCatalogEntity> listByGenre(Genre genre) throws CantGetEntityListException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            return movieDao.listByGenre(genre);

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
    public List<MovieCatalogEntity> listByCountry(Country country) throws CantGetEntityListException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            return movieDao.listByCountry(country);

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
    public Movie getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            return movieDao.getById(id);

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
    public void update(Movie movie) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            movieDao.alreadyExists(movie);
            MovieValidator.validate(movie);
            movieDao.update(movie);

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
    public void delete(Movie movie) throws CantDeleteEntityException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        movieDao.setConnection(connection);

        try{

            movieDao.delete(movie);

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
}
