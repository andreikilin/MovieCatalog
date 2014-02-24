package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.dao.GenreDao;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.GenreValidator;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static net.muzichko.moviecatalog.service.Utils.getConnection;

@Service("GenreService")
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private DataSource dataSource;

    static Logger log = Logger.getLogger(GenreServiceImpl.class);

    @Override
    public void add(Genre genre) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException {

        Connection connection = getConnection(dataSource, log);
        genreDao.setConnection(connection);

        try{

            genreDao.alreadyExists(genre);
            GenreValidator.validate(genre);
            genreDao.add(genre);

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
    public List<Genre> list() throws MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        genreDao.setConnection(connection);

        try{

            return genreDao.list();

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
    public Genre getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        genreDao.setConnection(connection);

        try{

            return genreDao.getById(id);

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
    public void update(Genre genre) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException {

        Connection connection = getConnection(dataSource, log);
        genreDao.setConnection(connection);

        try{

            genreDao.alreadyExists(genre);
            GenreValidator.validate(genre);
            genreDao.update(genre);

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
    public void delete(Genre genre) throws CantDeleteEntityException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        genreDao.setConnection(connection);

        try{

            genreDao.delete(genre);

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
