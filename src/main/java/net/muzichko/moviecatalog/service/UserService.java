package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface UserService {

    public void add(User user) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantAddEntityException;

    public List<MovieCatalogEntity> list() throws MovieCatalogSystemException, CantGetEntityListException;

    public User getById(int id) throws NoSuchEntityException, MovieCatalogSystemException, CantGetEntityListException;

    public void update(User user) throws MovieCatalogSystemException, EntityAlreadyExistsException, ValidationMovieCatalogException, CantUpdateEntityException;

    public void delete(User user) throws CantDeleteEntityException, MovieCatalogSystemException;

}
