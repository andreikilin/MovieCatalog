package net.muzichko.moviecatalog.dao;

import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;

import java.sql.Connection;
import java.util.List;

public interface GenreDao {

    public void add(Genre genre) throws CantAddEntityException, MovieCatalogSystemException;

    public List<MovieCatalogEntity> list() throws CantGetEntityListException, MovieCatalogSystemException;

    public Genre getById(int id) throws NoSuchEntityException, CantGetEntityListException, MovieCatalogSystemException;

    public void update(Genre genre) throws CantUpdateEntityException, MovieCatalogSystemException;

    public void delete(Genre genre) throws CantDeleteEntityException, MovieCatalogSystemException;

    public void alreadyExists(Genre genre) throws EntityAlreadyExistsException, MovieCatalogSystemException;

    public void fillNewIdFromDB(Genre genre) throws MovieCatalogSystemException;

    public void setConnection(Connection connection);

}
