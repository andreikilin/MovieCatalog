package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.Review;
import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;

import java.util.List;

public interface ReviewService {

    public void add(Review review) throws ValidationMovieCatalogException, CantAddEntityException, MovieCatalogSystemException;

    public List<Review> list() throws CantGetEntityListException;

    public List<Review> listByUser(User user) throws CantGetEntityListException;

    public List<Review> listByMovie(Movie movie) throws CantGetEntityListException;

    public Review getById(int id) throws CantGetEntityListException, NoSuchEntityException;

    public void update(Review review) throws ValidationMovieCatalogException, MovieCatalogSystemException, CantUpdateEntityException;

    public void delete(Review review) throws CantDeleteEntityException, MovieCatalogSystemException;

}
