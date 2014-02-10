package net.muzichko.moviecatalog.test;


import net.muzichko.moviecatalog.domain.Genre;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.GenreService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GenreTest {

    private ApplicationContext context;
    private GenreService genreService;

    @Before
    public void setUp(){

        context = new ClassPathXmlApplicationContext("context-test.xml");
        genreService = (GenreService) context.getBean("GenreService");

    }

    @Test
    public void testAddNewUniqueGenre() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genreService.add(genre);

        Genre genreFromDb = genreService.getById(genre.getId());

        if(!genre.equals(genreFromDb)){
            throw new AssertionError("New genre isn't equal to new genre from DB.");
        }
    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewGenreInvalidCharacters() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolNumbersString(20));
        genreService.add(genre);

    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testAddNewExistingGenre() throws MovieCatalogException {

        Genre genre = new Genre("Comedy");
        genreService.add(genre);

    }

    @Test
    public void testDeleteExistingGenre() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genreService.add(genre);
        genreService.delete(genre);

    }

    @Test (expected = CantDeleteEntityException.class)
    public void testDeleteNotExistingGenre() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genreService.delete(genre);

    }

    @Test
    public void testUpdateExistingGenreUniqueName() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genreService.add(genre);

        genre.setName(Utils.randomEnglishSymbolString(20));

        genreService.update(genre);

    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testUpdateExistingGenreNotUniqueName() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genreService.add(genre);

        genre.setName("Comedy");

        genreService.update(genre);

    }

    @Test (expected = CantUpdateEntityException.class)
    public void testUpdateNotExistingGenre() throws MovieCatalogException {

        Genre genre = new Genre(Utils.randomEnglishSymbolString(20));
        genre.setName(Utils.randomEnglishSymbolString(20));

        genreService.update(genre);

    }


}
