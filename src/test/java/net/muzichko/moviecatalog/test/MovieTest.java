package net.muzichko.moviecatalog.test;


import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.domain.MovieCatalogEntity;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.CountryService;
import net.muzichko.moviecatalog.service.GenreService;
import net.muzichko.moviecatalog.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static net.muzichko.moviecatalog.test.Utils.*;

public class MovieTest {

    private ApplicationContext context;
    private MovieService movieService;
    private CountryService countryService;
    private GenreService genreService;

    private final int ATTEMPT_COUNT = 20;

    @Before
    public void setUp(){

        context = new ClassPathXmlApplicationContext("context-test.xml");
        movieService = (MovieService) context.getBean("MovieService");
        countryService = (CountryService) context.getBean("CountryService");
        genreService = (GenreService) context.getBean("GenreService");

    }

    // ---------- ADDING -------------

    @Test
    public void testAddNewUniqueFilmCheckEquals() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            String description = randomCyrillicSymbolString(200);
            String starring = randomCyrillicSymbolString(200);
            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, description, starring, 2008, country);
            movieService.add(movie);

            Movie movieInDb = movieService.getById(movie.getId());

            if(!movie.equals(movieInDb)){
                throw new AssertionError("New movie isn't equal to new movie from DB.");
            }
        }
    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmNullCountry() throws MovieCatalogException {

        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, null);
        movieService.add(movie);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmNullGenre() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), null, "sdds", "sss", 2008, country);
        movieService.add(movie);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmNullCountryNullGenre() throws MovieCatalogException {

        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), null, "sdds", "sss", 2008, null);
        movieService.add(movie);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmIncorrectSymbols() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomIncorrectSymbolString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmIncorrectYear1() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomCyrillicSymbolString(50), genre, "sdds", "sss", 1500, country);

        movieService.add(movie);

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewUniqueFilmIncorrectYear2() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2050, country);

        movieService.add(movie);

    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testAddNewExistingFilm() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        String name = randomEnglishSymbolNumbersString(50);
        Movie movie = new Movie(name, genre, "sdds", "sss", 2008, country);
        movieService.add(movie);

        movie = new Movie(name, genre, "sdds", "sss", 2008, country);
        movieService.add(movie);
    }

    // ---------- DELETING -------------

    @Test
    public void testDeleteExistingMovie() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        movieService.delete(movie);

    }

    @Test (expected = CantDeleteEntityException.class)
    public void testDeleteNotExistingMovie() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.delete(movie);

    }


    // ---------- UPDATING -------------

    @Test
    public void testUpdateExistingMovie() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
        movieService.add(movie);

        country = countryService.getById(2);
        genre = genreService.getById(2);
        movie.setYear(2005);
        movie.setCountry(country);
        movie.setGenre(genre);
        movie.setName(randomCyrillicSymbolString(50));

        movieService.update(movie);
        Movie movieFromDb = movieService.getById(movie.getId());
        if(!movie.equals(movieFromDb)){
            throw new AssertionError("New movie isn't equal to new movie from DB.");
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testUpdateExistingMovieNotUniqueName() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        String oldName = randomEnglishSymbolNumbersString(50);
        Movie movie = new Movie(oldName, genre, "sdds", "sss", 2008, country);
        movieService.add(movie);

        movie.setName(randomCyrillicSymbolString(50));

        movieService.add(movie);
        movie.setName(oldName);
        movieService.update(movie);

    }

    @Test (expected = CantUpdateEntityException.class)
    public void testUpdateNotExistingMovie() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
        movieService.update(movie);

    }

    // ---------- LIST -------------
    // TODO: refactor like in review test

    @Test
    public void testListAddNewSize() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);

        int sizeBefore = movieService.list().size();
        for(int i = 0; i < ATTEMPT_COUNT; i++){

            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
            movieService.add(movie);

        }

        int sizeAfter = movieService.list().size();

        if(sizeAfter != (sizeBefore + ATTEMPT_COUNT)){
            throw new AssertionError("Size of list of movies hasn't been increased by " +
                    ATTEMPT_COUNT + " after adding " + ATTEMPT_COUNT + " movies.");
        }

    }

    @Test
    public void testListAddNewContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);

        List<Movie> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){

            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
            list.add(movie);
            movieService.add(movie);
        }

        List<MovieCatalogEntity> listFromDB = movieService.list();

        for(Movie curMovie : list){
            if(!listFromDB.contains(curMovie)){
                throw new AssertionError("List of movies from db isn't containing added movie");
            }
        }
    }

    @Test
    public void testListUpdateContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Genre genre2 = genreService.getById(2);

        List<Movie> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){

            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "description", "starring", 2008, country);
            movieService.add(movie);

            movie.setGenre(genre2);
            movie.setYear(2014);
            movieService.update(movie);
            list.add(movie);

        }

        List<MovieCatalogEntity> listFromDB = movieService.list();

        for(Movie curMovie : list){
            if(!listFromDB.contains(curMovie)){
                throw new AssertionError("List of movies from db isn't containing updated movie");
            }
        }
    }

    @Test
    public void testListSizeAfterDeleting() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);

        int sizeBefore = movieService.list().size();
        List<Movie> list = new ArrayList<>();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
            movieService.add(movie);
            list.add(movie);
        }

        int deleteCount = 0;
        for(Movie curMovie : list){
            if(deleteCount < ATTEMPT_COUNT/3){
                movieService.delete(curMovie);
                deleteCount++;
            }
        }

        int sizeAfter = movieService.list().size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT + ATTEMPT_COUNT/3)){
            throw new AssertionError("Wrong size of list after deleting");
        }
    }

    @Test
    public void testListAfterDeletingDoesntContain() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        movieService.delete(movie);

        List<MovieCatalogEntity> list = movieService.list();

        if(list.contains(movie)){
            throw new AssertionError("List of movies contains deleted movie.");
        }
    }

    // ---------- LIST BY YEAR -------------
    // TODO: refactor like in review test

    @Test
    public void testListByYearAddNewSize() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        int sizeBefore = movieService.listByYear(2008).size();

        movieService.add(movie);

        int sizeAfter = movieService.listByYear(2008).size();

        if(sizeAfter != (sizeBefore + 1)){
            throw new AssertionError("Size of list of movies by year hasn't been increased by 1 after adding one movie.");
        }

    }

    @Test
    public void testListByYearAddNewContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByYear(2008);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by year doesn't contains new movie.");
        }
    }

    @Test
    public void testListByYearUpdateContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2009, country);

        movieService.add(movie);
        movie.setYear(2008);
        movieService.update(movie);

        List<MovieCatalogEntity> list = movieService.listByYear(2008);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by year doesn't contains updated movie.");
        }
    }

    @Test
    public void testListByYearSizeAfterDeleting() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        int sizeBefore = movieService.listByYear(2008).size();
        movieService.delete(movie);
        int sizeAfter = movieService.listByYear(2008).size();

        if(sizeBefore != (sizeAfter + 1)){
            throw new AssertionError("Size of list of movies by year hasn't been decreased by 1 after deleting one movie.");
        }
    }

    @Test
    public void testListByYearAfterDeletingDoesntContain() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        movieService.delete(movie);

        List<MovieCatalogEntity> list = movieService.listByYear(2008);

        if(list.contains(movie)){
            throw new AssertionError("List of movies by year contains deleted movie.");
        }
    }

    @Test
    public void testListByYearCorrectYear() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);
        movieService.add(movie);

        movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2014, country);
        movieService.add(movie);

        movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2014, country);
        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByYear(2014);

        for (MovieCatalogEntity curMovie : list){
            if(((Movie)curMovie).getYear() != 2014){
                throw new AssertionError("List of movies by year contains wrong year.");
            }
        }

    }


    // ---------- LIST BY GENRE -------------
    // TODO: refactor like in review test

    @Test
    public void testListByGenreAddNewSize() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = new Genre(randomEnglishSymbolString(10));
        genreService.add(genre);

        int sizeBefore = movieService.listByGenre(genre).size();

        for(int i = 0; i < ATTEMPT_COUNT; i++){
            Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "description", "starring", 2008, country);
            movieService.add(movie);
        }

        int sizeAfter = movieService.listByGenre(genre).size();
        if(sizeBefore != (sizeAfter - ATTEMPT_COUNT)){
            throw new AssertionError("Size of list of movies hasn't been increased by " +
                    ATTEMPT_COUNT + " after adding " + ATTEMPT_COUNT + " reviews.");
        }
    }

    @Test
    public void testListByGenreAddNewContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByGenre(genre);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by genre doesn't contains new movie.");
        }
    }

    @Test
    public void testListByGenreUpdateContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Genre genre2 = genreService.getById(2);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2009, country);

        movieService.add(movie);
        movie.setGenre(genre2);
        movieService.update(movie);

        List<MovieCatalogEntity> list = movieService.listByGenre(genre2);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by genre doesn't contains updated movie.");
        }
    }

    @Test
    public void testListByGenreSizeAfterDeleting() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        int sizeBefore = movieService.listByGenre(genre).size();
        movieService.delete(movie);
        int sizeAfter = movieService.listByGenre(genre).size();

        if(sizeBefore != (sizeAfter + 1)){
            throw new AssertionError("Size of list of movies by genre hasn't been decreased by 1 after deleting one movie.");
        }
    }

    @Test
    public void testListByGenreAfterDeletingDoesntContain() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        movieService.delete(movie);

        List<MovieCatalogEntity> list = movieService.listByGenre(genre);

        if(list.contains(movie)){
            throw new AssertionError("List of movies by genre contains deleted movie.");
        }
    }

    @Test
    public void testListByGenreCorrectGenre() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre1 = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre1, "sdds", "sss", 2008, country);
        movieService.add(movie);

        country = countryService.getById(1);
        Genre genre2 = genreService.getById(2);
        movie = new Movie(randomEnglishSymbolNumbersString(50), genre2, "sdds", "sss", 2014, country);
        movieService.add(movie);

        country = countryService.getById(1);
        movie = new Movie(randomEnglishSymbolNumbersString(50), genre2, "sdds", "sss", 2014, country);
        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByGenre(genre2);

        for (MovieCatalogEntity curMovie : list){
            if(!genre2.equals(((Movie)curMovie).getGenre())){
                throw new AssertionError("List of movies by genre contains wrong genre.");
            }
        }

    }


    // ---------- LIST BY COUNTRY -------------
    // TODO: refactor like in review test

    @Test
    public void testListByCountryAddNewSize() throws MovieCatalogException {

        Country country = new Country(randomEnglishSymbolString(10));
        countryService.add(country);
        Genre genre = genreService.getById(1);

        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        int sizeBefore = movieService.listByCountry(country).size();

        if(sizeBefore != 0){
            throw new AssertionError("Size of list of movies by new country != 0");
        }

        movieService.add(movie);

        int sizeAfter = movieService.listByCountry(country).size();

        if(sizeAfter != 1){
            throw new AssertionError("Size of list of movies by new added country != 1");
        }

    }

    @Test
    public void testListByCountryAddNewContains() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByCountry(country);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by country doesn't contains new movie.");
        }
    }

    @Test
    public void testListByCountryUpdateContains() throws MovieCatalogException {

        Country country1 = countryService.getById(1);
        Country country2 = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2009, country1);

        movieService.add(movie);
        movie.setCountry(country2);
        movieService.update(movie);

        List<MovieCatalogEntity> list = movieService.listByCountry(country2);

        if(!list.contains(movie)){
            throw new AssertionError("List of movies by country doesn't contains updated movie.");
        }
    }

    @Test
    public void testListByCountrySizeAfterDeleting() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        int sizeBefore = movieService.listByCountry(country).size();
        movieService.delete(movie);
        int sizeAfter = movieService.listByCountry(country).size();

        if(sizeBefore != (sizeAfter + 1)){
            throw new AssertionError("Size of list of movies by country hasn't been decreased by 1 after deleting one movie.");
        }
    }

    @Test
    public void testListByCountryAfterDeletingDoesntContain() throws MovieCatalogException {

        Country country = countryService.getById(1);
        Genre genre = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre, "sdds", "sss", 2008, country);

        movieService.add(movie);
        movieService.delete(movie);

        List<MovieCatalogEntity> list = movieService.listByCountry(country);

        if(list.contains(movie)){
            throw new AssertionError("List of movies by country contains deleted movie.");
        }
    }

    @Test
    public void testListByCountryCorrectCountry() throws MovieCatalogException {

        Country country1 = countryService.getById(1);
        Genre genre1 = genreService.getById(1);
        Movie movie = new Movie(randomEnglishSymbolNumbersString(50), genre1, "sdds", "sss",  2008, country1);
        movieService.add(movie);

        Country country2 = countryService.getById(1);
        Genre genre2 = genreService.getById(2);
        movie = new Movie(randomEnglishSymbolNumbersString(50), genre2, "sdds", "sss",  2014, country2);
        movieService.add(movie);

        movie = new Movie(randomEnglishSymbolNumbersString(50), genre2, "sdds", "sss",  2014, country2);
        movieService.add(movie);

        List<MovieCatalogEntity> list = movieService.listByCountry(country2);

        for (MovieCatalogEntity curMovie : list){
            if(!country2.equals(((Movie)curMovie).getCountry())){
                throw new AssertionError("List of movies by country contains wrong country.");
            }
        }

    }


}
