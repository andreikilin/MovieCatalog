package net.muzichko.moviecatalog.dao;


import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.Review;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface ReviewDao {

    public void add(Review review) throws CantAddEntityException, MovieCatalogSystemException;

    public List<Review> list() throws CantGetEntityListException;

    public List<Review> listByUser(User user) throws CantGetEntityListException;

    public List<Review> listByMovie(Movie movie) throws CantGetEntityListException;

    public Review getById(int id) throws NoSuchEntityException, CantGetEntityListException;

    public void update(Review review) throws CantUpdateEntityException, MovieCatalogSystemException;

    public void delete(Review review) throws CantDeleteEntityException, MovieCatalogSystemException;

    public void fillNewIdFromDB(Review review) throws MovieCatalogSystemException;

    public void fillNewTimestampFromDB(Review review) throws MovieCatalogSystemException;


    // TODO: get fields map
}
