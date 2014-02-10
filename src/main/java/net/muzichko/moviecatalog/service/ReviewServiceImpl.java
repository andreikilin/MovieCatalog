package net.muzichko.moviecatalog.service;


import net.muzichko.moviecatalog.dao.ReviewDao;
import net.muzichko.moviecatalog.domain.*;
import net.muzichko.moviecatalog.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
            readOnly = false, rollbackFor = MovieCatalogException.class)
    public void add(Review review) throws ValidationMovieCatalogException, CantAddEntityException, MovieCatalogSystemException {

        // no check exists for review

        ReviewValidator.validate(review);
        reviewDao.add(review);

    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<MovieCatalogEntity> list() throws CantGetEntityListException {

        return reviewDao.list();

    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<MovieCatalogEntity> listByUser(User user) throws CantGetEntityListException {

        return reviewDao.listByUser(user);

    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<MovieCatalogEntity> listByMovie(Movie movie) throws CantGetEntityListException {

        return reviewDao.listByMovie(movie);

    }

    @Override
    public MovieCatalogEntity getById(int id) throws CantGetEntityListException, NoSuchEntityException {

        return reviewDao.getById(id);

    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
            readOnly = false, rollbackFor = MovieCatalogException.class)
    public void update(Review review) throws ValidationMovieCatalogException, MovieCatalogSystemException, CantUpdateEntityException {

        // no check exists fo review

        ReviewValidator.validate(review);
        reviewDao.update(review);

    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
            readOnly = false, rollbackFor = MovieCatalogException.class)
    public void delete(Review review) throws CantDeleteEntityException, MovieCatalogSystemException {

        reviewDao.delete(review);

    }
}
