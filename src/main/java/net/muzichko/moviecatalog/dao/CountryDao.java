package net.muzichko.moviecatalog.dao;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;

import java.sql.Connection;
import java.util.List;

public interface CountryDao {

    public void add(Country country) throws CantAddEntityException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> list() throws CantGetEntityListException, MovieCatalogSystemException;

    public Country  getById(int id) throws CantGetEntityListException, NoSuchEntityException, MovieCatalogSystemException;

    public void update(Country country) throws CantUpdateEntityException, MovieCatalogSystemException;

    public void delete(Country country) throws CantDeleteEntityException, MovieCatalogSystemException;

    public void alreadyExists(Country country) throws EntityAlreadyExistsException, MovieCatalogSystemException;

    public void fillNewIdFromDB(Country country) throws MovieCatalogSystemException;

    public void setConnection(Connection connection);

}
