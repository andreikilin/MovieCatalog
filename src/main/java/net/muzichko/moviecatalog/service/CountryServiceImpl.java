package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.dao.CountryDao;
import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.CountryValidator;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static net.muzichko.moviecatalog.service.Utils.getConnection;

@Service("CountryService")
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private DataSource dataSource;

    static Logger log = Logger.getLogger(CountryServiceImpl.class);

    @Override
    public void add(Country country) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException {

        Connection connection = getConnection(dataSource, log);
        countryDao.setConnection(connection);

        try{

            countryDao.alreadyExists(country);
            CountryValidator.validate(country);
            countryDao.add(country);

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
        countryDao.setConnection(connection);

        try{

            return countryDao.list();

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
    public Country getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException {

        Connection connection = getConnection(dataSource, log);
        countryDao.setConnection(connection);

        try{

            return countryDao.getById(id);

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
    public void update(Country country) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException {

        Connection connection = getConnection(dataSource, log);
        countryDao.setConnection(connection);

        try{

            countryDao.alreadyExists(country);
            CountryValidator.validate(country);
            countryDao.update(country);

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
    public void delete(Country country) throws CantDeleteEntityException, MovieCatalogSystemException {

        Connection connection = getConnection(dataSource, log);
        countryDao.setConnection(connection);

        try{

            countryDao.delete(country);

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
