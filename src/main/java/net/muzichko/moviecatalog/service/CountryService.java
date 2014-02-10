package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface CountryService {

    public void add(Country country) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException;

    public List<MovieCatalogEntity> list() throws MovieCatalogSystemException, CantGetEntityListException;

    public Country getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException;

    public void update(Country country) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException;

    public void delete(Country country) throws CantDeleteEntityException, MovieCatalogSystemException;
}
