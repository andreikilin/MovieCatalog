package net.muzichko.moviecatalog.test;

import net.muzichko.moviecatalog.domain.*;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static net.muzichko.moviecatalog.test.Utils.*;

public class ReviewTest {

    private ApplicationContext context;
    private ReviewService reviewService;
    private MovieService movieService;
    private UserService userService;
    private CountryService countryService;
    private GenreService genreService;

    private final int ATTEMPT_COUNT = 20;

    @Before
    public void setUp(){

        context = new ClassPathXmlApplicationContext("context-test.xml");
        reviewService = (ReviewService) context.getBean("ReviewService");
        movieService = (MovieService) context.getBean("MovieService");
        userService = (UserService) context.getBean("UserService");
        countryService = (CountryService) context.getBean("CountryService");
        genreService = (GenreService) context.getBean("GenreService");

    }

    // ---------- ADDING -------------

    @Test
    public void testAddReviewCheckEquals() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);

            Review reviewInDb = (Review)reviewService.getById(review.getId());

            if(!review.equals(reviewInDb)){
                throw new AssertionError("New review isn't equal to new review from DB.");
            }
        }
    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewNullMovie() throws MovieCatalogException {

        User user = userService.getById(2);

        Review review = new Review(null, user, true, 80, randomCyrillicSymbolString(200));
        reviewService.add(review);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewNullUser() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        Review review = new Review(movie, null, true, 80, randomCyrillicSymbolString(200));
        reviewService.add(review);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewNullMovieNullUser() throws MovieCatalogException {

        Review review = new Review(null, null, true, 80, randomCyrillicSymbolString(200));
        reviewService.add(review);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewIncorrectSymbols() throws MovieCatalogException {

        Movie movie = movieService.getById(1);
        User user = userService.getById(1);

        Review review = new Review(movie, user, true, 100, randomIncorrectSymbolString(10));
        reviewService.add(review);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewIncorrectRating1() throws MovieCatalogException {

        Movie movie = movieService.getById(1);
        User user = userService.getById(1);

        Review review = new Review(movie, user, true, 150, "comment");
        reviewService.add(review);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddReviewIncorrectRating2() throws MovieCatalogException {

        Movie movie = movieService.getById(1);
        User user = userService.getById(1);

        Review review = new Review(movie, user, true, -150, "comment");
        reviewService.add(review);

    }

    // ---------- DELETING -------------
    @Test
    public void testDeleteExistingReviewTest() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);
        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
            reviewService.delete(review);

            try {
                reviewService.getById(review.getId());
            } catch (CantGetEntityListException ignore) {
            /*NOP*/
            }
        }
    }

    @Test (expected = CantDeleteEntityException.class)
    public void testDeleteNotExistingReview() throws MovieCatalogException {

        Movie movie = movieService.getById(1);
        User user = userService.getById(1);

        Review review = new Review(movie, user, true, 100, "comment comment");
        reviewService.delete(review);

    }


    // ---------- UPDATING -------------

    @Test
    public void testUpdateExistingReview() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);

            review.setComment(randomCyrillicSymbolString(200));
            reviewService.update(review);

            Review reviewInDb = (Review)reviewService.getById(review.getId());

            if(!review.equals(reviewInDb)){
                throw new AssertionError("New review isn't equal to new review from DB.");
            }
        }
    }

    @Test (expected = CantUpdateEntityException.class)
    public void testUpdateNotExistingReview() throws MovieCatalogException
    {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
        reviewService.update(review);

    }


    @Test (expected = ValidationMovieCatalogException.class)
    public void testUpdateExistingReviewIncorrectSymbols() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        Review review = new Review(movie, user, true, 80, randomEnglishSymbolString(200));
        reviewService.add(review);

        review.setComment(randomIncorrectSymbolString(200));
        reviewService.update(review);

    }

    // ---------- LIST -------------

    @Test
    public void testListAddSize() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        int sizeBefore = reviewService.list().size();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
        }

        int sizeAfter = reviewService.list().size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT)){
            throw new AssertionError("Size of list of reviews hasn't been increased by " +
                    ATTEMPT_COUNT + " after adding " + ATTEMPT_COUNT + " reviews.");
        }

    }

    @Test
    public void testListAddContains() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> listFromDB = reviewService.list();

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews from db isn't containing added review");
            }
        }

    }

    @Test
    public void testListUpdateContains() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);
        User user2 = userService.getById(1);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);

            review.setUser(user2);
            review.setViewed(false);
            reviewService.update(review);
            list.add(review);
        }

        List<Review> listFromDB = reviewService.list();

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews from db isn't containing updated review");
            }
        }

    }

    @Test
    public void testListSizeAfterDeleting() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        int sizeBefore = reviewService.list().size();
        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount < ATTEMPT_COUNT/3){
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        int sizeAfter = reviewService.list().size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT + ATTEMPT_COUNT/3)){
            throw new AssertionError("Wrong size of list after deleting");
        }

    }

    @Test
    public void testListAfterDeletingDoesntContain() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> deletedList = new ArrayList<>();
        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount <= ATTEMPT_COUNT/3){
                deletedList.add(curReview);
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        List<Review> listFromDB = reviewService.list();
        for(Review curReview : deletedList){
            if(listFromDB.contains(curReview)){
                throw new AssertionError("List from DB contains deleted review");
            }
        }


    }

    // ---------- LIST BY USER -------------

    @Test
    public void testListByUserAddSize() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        int sizeBefore = reviewService.listByUser(user).size();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
        }

        int sizeAfter = reviewService.listByUser(user).size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT)){
            throw new AssertionError("Size of list of reviews hasn't been increased by " +
                    ATTEMPT_COUNT + " after adding " + ATTEMPT_COUNT + " reviews.");
        }
    }

    @Test
    public void testListByUserAddContains() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> listFromDB = reviewService.listByUser(user);

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews from db isn't containing added review");
            }
        }

    }

    @Test
    public void testListByUserUpdateContains() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);
        User user2 = userService.getById(1);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);

            review.setUser(user2);
            review.setViewed(false);
            reviewService.update(review);
            list.add(review);
        }

        List<Review> listFromDB = reviewService.listByUser(user2);

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews from db isn't containing updated review");
            }
        }

    }

    @Test
    public void testListByUserSizeAfterDeleting() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        int sizeBefore = reviewService.listByUser(user).size();
        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount < ATTEMPT_COUNT/3){
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        int sizeAfter = reviewService.listByUser(user).size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT + ATTEMPT_COUNT/3)){
            throw new AssertionError("Wrong size of list by user after deleting");
        }

    }

    @Test
    public void testListByUserAfterDeletingDoesntContain() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> deletedList = new ArrayList<>();
        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount <= ATTEMPT_COUNT/3){
                deletedList.add(curReview);
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        List<Review> listFromDB = reviewService.listByUser(user);
        for(Review curReview : deletedList){
            if(listFromDB.contains(curReview)){
                throw new AssertionError("List from DB contains deleted review");
            }
        }
    }

    @Test
    public void testListByUserCorrectUser() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user1 = userService.getById(1);
        User user2 = userService.getById(2);

        for(int i = 0; i < ATTEMPT_COUNT/2; i++){
            Review review = new Review(movie, user1, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
            review = new Review(movie, user2, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
        }

        List<Review> list = reviewService.listByUser(user1);
        for(Review curReview : list){
            if(!user1.equals(((Review)curReview).getUser())){
                throw new AssertionError("List by user contains review with wrong user");
            }
        }

    }

    // ---------- LIST BY MOVIE -------------

    @Test
    public void testListByMovieAddSize() throws MovieCatalogException {

        Movie movie = movieService.getById(1);

        int sizeBefore = reviewService.listByMovie(movie).size();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
            userService.add(user);
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
        }

        int sizeAfter = reviewService.listByMovie(movie).size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT)){
            throw new AssertionError("Size of list of reviews by movie hasn't been increased by " +
                    ATTEMPT_COUNT + " after adding " + ATTEMPT_COUNT + " reviews.");
        }
    }

    @Test
    public void testListByMovieAddContains() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> listFromDB = reviewService.listByMovie(movie);

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews by movie from db isn't containing added review");
            }
        }
    }

    @Test
    public void testListByMovieUpdateContains() throws MovieCatalogException {

        Movie movie1 = movieService.getById(1);
        Movie movie2 = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie1, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);

            review.setMovie(movie2);
            review.setViewed(false);
            reviewService.update(review);
            list.add(review);
        }

        List<Review> listFromDB = reviewService.listByMovie(movie2);

        for(Review curReview : list){
            if(!listFromDB.contains(curReview)){
                throw new AssertionError("List of reviews by movie from db isn't containing updated review " + curReview.toString());
            }
        }
    }

    @Test
    public void testListByMovieSizeAfterDeleting() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        int sizeBefore = reviewService.listByMovie(movie).size();
        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount < ATTEMPT_COUNT/3){
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        int sizeAfter = reviewService.listByMovie(movie).size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT + ATTEMPT_COUNT/3)){
            throw new AssertionError("Wrong size of review list by movie after deleting");
        }
    }

    @Test
    public void testListByMovieAfterDeletingDoesntContain() throws MovieCatalogException {

        Movie movie = movieService.getById(2);
        User user = userService.getById(2);

        List<Review> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Review review = new Review(movie, user, true, 80, randomCyrillicSymbolString(200));
            list.add(review);
            reviewService.add(review);
        }

        List<Review> deletedList = new ArrayList<>();
        int deleteCount = 0;
        for(Review curReview : list){
            if(deleteCount <= ATTEMPT_COUNT/3){
                deletedList.add(curReview);
                reviewService.delete(curReview);
                deleteCount++;
            }
        }

        List<Review> listFromDB = reviewService.listByMovie(movie);
        for(Review curReview : deletedList){
            if(listFromDB.contains(curReview)){
                throw new AssertionError("List by movie from DB contains deleted review");
            }
        }
    }

    @Test
    public void testListByMovieCorrectMovie() throws MovieCatalogException {

        Movie movie1 = movieService.getById(1);
        Movie movie2 = movieService.getById(2);
        User user = userService.getById(2);

        for(int i = 0; i < ATTEMPT_COUNT/2; i++){
            Review review = new Review(movie1, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
            review = new Review(movie2, user, true, 80, randomCyrillicSymbolString(200));
            reviewService.add(review);
        }

        List<Review> list = reviewService.listByMovie(movie1);
        for(Review curReview : list){
            if(!movie1.equals(((Review)curReview).getMovie())){
                throw new AssertionError("List by movie contains review with wrong movie");
            }
        }
    }
}
