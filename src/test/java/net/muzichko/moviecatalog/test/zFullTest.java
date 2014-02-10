package net.muzichko.moviecatalog.test;

import net.muzichko.moviecatalog.domain.*;
import net.muzichko.moviecatalog.exception.MovieCatalogException;
import net.muzichko.moviecatalog.service.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static net.muzichko.moviecatalog.test.Utils.randomCyrillicSymbolString;
import static net.muzichko.moviecatalog.test.Utils.randomEmail;
import static net.muzichko.moviecatalog.test.Utils.randomEnglishSymbolString;

public class zFullTest {

    private ApplicationContext context;
    private ReviewService reviewService;
    private MovieService movieService;
    private UserService userService;
    private CountryService countryService;
    private GenreService genreService;

    private final int ATTEMPT_COUNT = 200;

    @Before
    public void setUp(){

        context = new ClassPathXmlApplicationContext("context-test.xml");
        reviewService = (ReviewService) context.getBean("ReviewService");
        movieService = (MovieService) context.getBean("MovieService");
        userService = (UserService) context.getBean("UserService");
        countryService = (CountryService) context.getBean("CountryService");
        genreService = (GenreService) context.getBean("GenreService");

    }

    @Test
    public void mainTest() throws MovieCatalogException {

        for(int i = 0; i < ATTEMPT_COUNT; i++){

            User user1 = new User(randomEnglishSymbolString(15), randomEnglishSymbolString(10), randomEmail());
            userService.add(user1);
            user1.setEmail(randomEmail());
            userService.update(user1);

            Country country1 = new Country(randomCyrillicSymbolString(50));
            countryService.add(country1);

            Country country2 = new Country(randomCyrillicSymbolString(50));
            countryService.add(country2);

            Genre genre1 = new Genre(randomCyrillicSymbolString(50));
            genreService.add(genre1);

            Genre genre2 = new Genre(randomCyrillicSymbolString(50));
            genreService.add(genre2);

            Movie movie1 = new Movie(randomCyrillicSymbolString(50), genre1, "description", "starring", 2005, country1);
            movieService.add(movie1);

            Movie movie2 = new Movie(randomCyrillicSymbolString(50), genre2, "description", "starring", 2005, country2);
            movieService.add(movie2);

            Review review1 = new Review(movie2, user1, true, 90, randomCyrillicSymbolString(100));
            reviewService.add(review1);
            review1.setComment("cool");
            reviewService.update(review1);

            movie2.setYear(2006);
            movieService.update(movie2);

            User user2 = new User(randomEnglishSymbolString(15), randomEnglishSymbolString(10), randomEmail());
            userService.add(user2);

            user2.setPassword(randomEnglishSymbolString(10));
            userService.update(user2);

            review1.setRating(95);
            reviewService.update(review1);

            // TODO: it should be completed
        }

    }

}
