package net.muzichko.moviecatalog.dao;

import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;

import java.sql.Connection;
import java.util.List;

public interface MovieDao {

    public void add(Movie movie) throws CantAddEntityException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> list() throws CantGetEntityListException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> listByYear(int year) throws CantGetEntityListException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> listByGenre(Genre genre) throws MovieCatalogSystemException, CantGetEntityListException;

    public List<MovieCatalogEntity> listByCountry(Country country) throws MovieCatalogSystemException, CantGetEntityListException;

    public Movie getById(int id) throws NoSuchEntityException, CantGetEntityListException, MovieCatalogSystemException;

    public void update(Movie movie) throws CantUpdateEntityException, MovieCatalogSystemException;

    public void delete(Movie movie) throws CantDeleteEntityException, MovieCatalogSystemException;

    public void alreadyExists(Movie movie) throws EntityAlreadyExistsException, MovieCatalogSystemException;

    public void fillNewIdFromDB(Movie movie) throws MovieCatalogSystemException;

    public void setConnection(Connection connection);

    // TODO: get fields map
}
