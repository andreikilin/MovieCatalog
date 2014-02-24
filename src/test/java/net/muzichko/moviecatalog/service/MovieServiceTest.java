package net.muzichko.moviecatalog.service;

import static org.junit.Assert.*;

import net.muzichko.moviecatalog.domain.Movie;
import net.muzichko.moviecatalog.exception.CantDeleteEntityException;
import net.muzichko.moviecatalog.exception.CantGetEntityListException;
import net.muzichko.moviecatalog.exception.CantUpdateEntityException;
import net.muzichko.moviecatalog.exception.EntityAlreadyExistsException;
import net.muzichko.moviecatalog.exception.MovieCatalogSystemException;
import net.muzichko.moviecatalog.exception.NoSuchEntityException;
import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/ApplicationContext.xml" })
public class MovieServiceTest {

	@Autowired
	MovieService movieService;

	@Autowired
	CountryService countryService;

	@Autowired
	GenreService genreService;

	private final int movieId = 3;
	private final String movieName = "Ghost rider";
	private final int genreId = 4;
	private final String description = "Stunt motorcyclist Johnny Blaze gives up his soul"
			+ " to become a hellblazing vigilante, to fight against"
			+ " power hungry Blackheart, the son of the devil himself.";
	private final String starring = "Nicolas Cage, Eva Mendes, Sam Elliott";
	private final int year = 2007;
	private final int countryId = 1;
	private Movie movie;

	@Before
	public void setUp() throws Exception {
		movie = new Movie(movieId, movieName, genreService.getById(genreId),
				description, starring, year, countryService.getById(countryId));
		movieService.add(movie);
	}

	@After
	public void tearDown() throws Exception {
		Movie movie = movieService.getById(movieId);
		if (movie != null)
			movieService.delete(movie);
	}

	@Test
	public void testAdd() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		Movie found = movieService.getById(movieId);
		assertEquals(found, movie);
	}

	@Test
	public void testList() throws MovieCatalogSystemException,
			CantGetEntityListException {

		assertNotNull(movieService.list());
		assertTrue(movieService.list().contains(movie));
	}

	@Test
	public void testListByYear() throws MovieCatalogSystemException,
			CantGetEntityListException {

		assertNotNull(movieService.listByYear(year));
		assertTrue(movieService.listByYear(year).contains(movie));
	}

	@Test
	public void testListByGenre() throws CantGetEntityListException,
			MovieCatalogSystemException, NoSuchEntityException {

		assertNotNull(movieService.listByGenre(genreService.getById(genreId)));
		assertTrue(movieService.listByGenre(genreService.getById(genreId))
				.contains(movie));
	}

	@Test
	public void testListByCountry() throws CantGetEntityListException,
			MovieCatalogSystemException {

		assertNotNull(movieService.listByCountry(countryService
				.getById(countryId)));
		assertTrue(movieService
				.listByCountry(countryService.getById(countryId)).contains(
						movie));
	}

	@Test
	public void testGetById() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		Movie found = movieService.getById(movieId);
		assertNotNull(found);
		assertEquals(found, movie);
	}

	@Test
	public void testUpdate() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			EntityAlreadyExistsException, ValidationMovieCatalogException,
			CantUpdateEntityException {

		Movie found = movieService.getById(movieId);
		assertNotNull(found);
		assertEquals(found, movie);

		int genreIdNew = 2;
		int countryIdNew = 2;
		Movie movieNew = new Movie(movieId, movieName,
				genreService.getById(genreIdNew), description, starring, year,
				countryService.getById(countryIdNew));
		movieService.update(movieNew);
		Movie foundNew = movieService.getById(movieId);
		assertNotNull(foundNew);
		assertEquals(foundNew, movieNew);
	}

	@Test
	public void testDelete() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			CantDeleteEntityException {

		assertNotNull(movieService.getById(movieId));
		movieService.delete(movie);
		assertNull(movieService.getById(movieId));
	}
}
