package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface GenreService {

    public void add(Genre genre) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException;

    public List<Genre> list() throws MovieCatalogSystemException, CantGetEntityListException;

    public Genre getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException;

    public void update(Genre genre) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException;

    public void delete(Genre genre) throws CantDeleteEntityException, MovieCatalogSystemException;

}
