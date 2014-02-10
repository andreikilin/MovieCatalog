package net.muzichko.moviecatalog.dao;


import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    public void add(User user) throws CantAddEntityException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> list() throws CantGetEntityListException, MovieCatalogSystemException;

    public User getById(int id) throws NoSuchEntityException, CantGetEntityListException, MovieCatalogSystemException;

    public void update(User user) throws CantUpdateEntityException, MovieCatalogSystemException;

    public void delete(User user) throws CantDeleteEntityException, MovieCatalogSystemException;

    public void alreadyExists(User user) throws EntityAlreadyExistsException, MovieCatalogSystemException;

    public void fillNewIdFromDB(User user) throws MovieCatalogSystemException;

    public void setConnection(Connection connection);

}
