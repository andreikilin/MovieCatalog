package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface MovieService {

    public void add(Movie movie) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException;

    public List<MovieCatalogEntity> list() throws MovieCatalogSystemException, CantGetEntityListException;

    public List<MovieCatalogEntity> listByYear(int year) throws MovieCatalogSystemException, CantGetEntityListException;

    public List<MovieCatalogEntity> listByGenre(Genre genre) throws CantGetEntityListException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> listByCountry(Country country) throws CantGetEntityListException, MovieCatalogSystemException;

    public Movie getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException;

    public void update(Movie movie) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException;

    public void delete(Movie movie) throws CantDeleteEntityException, MovieCatalogSystemException;

}
