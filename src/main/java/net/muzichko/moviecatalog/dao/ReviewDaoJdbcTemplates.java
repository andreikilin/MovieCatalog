package net.muzichko.moviecatalog.dao;


import net.muzichko.moviecatalog.domain.*;
import net.muzichko.moviecatalog.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class ReviewDaoJdbcTemplates implements ReviewDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;       // TODO: connection exception ????????

    static Logger log = Logger.getLogger(ReviewDaoJdbcTemplates.class);

    static final class ReviewMapper implements RowMapper<MovieCatalogEntity> {

        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {

            Genre genre = new Genre(rs.getInt("idgenre"), rs.getString("genre"));

            Country country = new Country(rs.getInt("idcountry"), rs.getString("country"));

            User user = new User(rs.getInt("iduser"), rs.getString("login"),
                    rs.getString("password"), rs.getString("email"));

            Movie movie = new Movie(rs.getInt("idmovie"),
                    rs.getString("name"),
                    genre,
                    rs.getString("description"),
                    rs.getString("starring"),
                    rs.getInt("year"),
                    country);

            return new Review(rs.getInt("id"), movie, user, rs.getBoolean("viewed"),
                    rs.getInt("rating"), rs.getString("comment"), rs.getTimestamp("ts"));

        }
    }

    @Override
    public void add(Review review) throws CantAddEntityException, UnablePerformDBOperation {

        log.info("Adding review " + review.toString());

        String query = "insert into reviews(idmovie, iduser, viewed, rating, comment)" +
                " values(?, ?, ?, ?, ?); ";

        String baseErrorMessage = "Review wasn't added in DB " + review.toString() + ". ";

        try {
            int ok = jdbcTemplate.update(query, review.getMovie().getId(), review.getUser().getId(),
                    review.isViewed(), review.getRating(), review.getComment());

            if (ok == 0) {
                String errorMessage = baseErrorMessage + " jdbcTemplate.update(" + query + ", ... args) returns 0.";
                log.error(errorMessage);
                throw new CantAddEntityException(errorMessage);
            } else {
                fillNewIdFromDB(review);
                fillNewTimestampFromDB(review);
            }
        } catch (CantAddEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public List<MovieCatalogEntity> list() throws CantGetEntityListException {

        String query = "select r.*, u.*, m.*, c.name as country, g.name as genre, rr.average_rating from " +
                "reviews as r " +
                "left join (select reviews.idmovie, avg(reviews.rating) as average_rating " +
                "from reviews group by reviews.idmovie) as rr on r.idmovie = rr.idmovie " +
                "left join  users as u on r.iduser = u.id " +
                "left join  movies as m on r.idmovie = m.id " +
                "left join countries as c on m.idcountry = c.id " +
                "left join genres as g on m.idgenre = g.id " +
                "order by ts desc;";

        String baseErrorMessage = "Couldn't get list of reviews. ";

        try{
            return jdbcTemplate.query(query, new ReviewMapper());
        } catch (Exception e) {
            log.error(baseErrorMessage);
            throw new CantGetEntityListException(baseErrorMessage, e);
        }
    }

    @Override
    public List<MovieCatalogEntity> listByUser(User user) throws CantGetEntityListException {

        String query = "select r.*, u.*, m.*, c.name as country, g.name as genre, rr.average_rating from " +
                "reviews as r " +
                "left join (select reviews.idmovie, avg(reviews.rating) as average_rating " +
                "from reviews group by reviews.idmovie) as rr on r.idmovie = rr.idmovie " +
                "left join  users as u on r.iduser = u.id " +
                "left join  movies as m on r.idmovie = m.id " +
                "left join countries as c on m.idcountry = c.id " +
                "left join genres as g on m.idgenre = g.id " +
                "where r.iduser = ? order by ts desc;";

        String baseErrorMessage = "Couldn't get list of reviews by user " + user.toString() + ". ";

        try{
            return jdbcTemplate.query(query, new ReviewMapper(), user.getId());
        } catch (Exception e) {
            log.error(baseErrorMessage);
            throw new CantGetEntityListException(baseErrorMessage, e);
        }
    }

    @Override
    public List<MovieCatalogEntity> listByMovie(Movie movie) throws CantGetEntityListException {

        String query = "select r.*, u.*, m.*, c.name as country, g.name as genre, rr.average_rating from " +
                "reviews as r " +
                "left join (select reviews.idmovie, avg(reviews.rating) as average_rating " +
                "from reviews group by reviews.idmovie) as rr on r.idmovie = rr.idmovie " +
                "left join  users as u on r.iduser = u.id " +
                "left join  movies as m on r.idmovie = m.id " +
                "left join countries as c on m.idcountry = c.id " +
                "left join genres as g on m.idgenre = g.id " +
                "where r.idmovie = ? order by ts desc;";

        String baseErrorMessage = "Couldn't get list of reviews by movie " + movie.toString() + ". ";

        try{
            return jdbcTemplate.query(query, new ReviewMapper(), movie.getId());
        } catch (Exception e) {
            log.error(baseErrorMessage);
            throw new CantGetEntityListException(baseErrorMessage, e);
        }
    }

    @Override
    public MovieCatalogEntity getById(int id) throws NoSuchEntityException, CantGetEntityListException {

        String query = "select r.*, u.*, m.*, c.name as country, g.name as genre, rr.average_rating from " +
                "reviews as r " +
                "left join (select reviews.idmovie, avg(reviews.rating) as average_rating " +
                "from reviews group by reviews.idmovie) as rr on r.idmovie = rr.idmovie " +
                "left join  users as u on r.iduser = u.id " +
                "left join  movies as m on r.idmovie = m.id " +
                "left join countries as c on m.idcountry = c.id " +
                "left join genres as g on m.idgenre = g.id " +
                "where r.id = ? order by ts desc;";

        String baseErrorMessage = "Couldn't get review by id = " + id + ". ";

        try{
            MovieCatalogEntity review = jdbcTemplate.queryForObject(query, new ReviewMapper(), id);
            if(review != null){
                return review;
            } else {
                String errorMessage = baseErrorMessage + "No such review in DB.";
                log.error(errorMessage);
                throw new NoSuchEntityException(errorMessage);
            }
        } catch (NoSuchEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new CantGetEntityListException(baseErrorMessage + query, e);
        }
    }

    @Override
    public void update(Review review) throws CantUpdateEntityException, UnablePerformDBOperation {

        log.info("Updating review " + review.toString());

        String query = "update reviews set idmovie = ?, iduser = ?, viewed = ?, rating = ?, comment = ? where id = ?;";

        String baseErrorMessage = "Couldn't update review: " + review.toString() + ". ";

        try {
            int ok = jdbcTemplate.update(query, review.getMovie().getId(), review.getUser().getId(),
                    review.isViewed(), review.getRating(), review.getComment(), review.getId());

            if (ok == 0) {
                String errorMessage = baseErrorMessage + " jdbcTemplate.update(" + query + ", ... args) returns 0.";
                log.error(errorMessage);
                throw new CantUpdateEntityException(errorMessage);
            } else {
                fillNewTimestampFromDB(review);
            }
        } catch (CantUpdateEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public void delete(Review review) throws CantDeleteEntityException, UnablePerformDBOperation {

        log.info("Deleting review " + review.toString());

        String query = "delete from reviews where id = ?;";

        String baseErrorMessage = "Couldn't delete review: " + review.toString() + ". ";

        try {
            int ok = jdbcTemplate.update(query, review.getId());

            if (ok == 0) {
                String errorMessage = baseErrorMessage + " jdbcTemplate.update(" + query + ", ... args) returns 0.";
                log.error(errorMessage);
                throw new CantDeleteEntityException(errorMessage);
            }
        } catch (CantDeleteEntityException e) {
            throw e;
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public void fillNewIdFromDB(Review review) throws UnablePerformDBOperation {

        log.info("Filling the new id in the review " + review.toString());

        String query = "select id from reviews where iduser = ? and idmovie = ? order by id desc limit 1;";

        String baseErrorMessage = "New id wasn't filled in the review " + review.toString() + ". ";

        try{
            int newId = jdbcTemplate.queryForObject(query, Integer.class, review.getUser().getId(), review.getMovie().getId());
            review.setId(newId);
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

    @Override
    public void fillNewTimestampFromDB(Review review) throws UnablePerformDBOperation {

        log.info("Filling the new timestamp in the review " + review.toString());

        String query = "select ts from reviews where id = ?;";

        String baseErrorMessage = "New timestamp wasn't filled in the review " + review.toString() + ". ";

        try{
            Timestamp newTS = jdbcTemplate.queryForObject(query, Timestamp.class, review.getId());
            review.setTimestamp(newTS);
        } catch (Exception e) {
            log.error(baseErrorMessage + query);
            throw new UnablePerformDBOperation(baseErrorMessage + query, e);
        }
    }

}
