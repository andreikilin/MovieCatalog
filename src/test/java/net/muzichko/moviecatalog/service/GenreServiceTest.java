package net.muzichko.moviecatalog.service;

import static org.junit.Assert.*;
import net.muzichko.moviecatalog.domain.Genre;
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
public class GenreServiceTest {

	@Autowired
	GenreService genreService;

	private final int id = 1;
	private final String name = "TestGenre";
	private Genre genre;

	@Before
	public void setUp() throws Exception {
		genre = new Genre(id, name);
		genreService.add(genre);
	}

	@After
	public void tearDown() throws Exception {
		Genre genre = genreService.getById(id);
		if (genre != null)
			genreService.delete(genre);
	}

	@Test
	public void testAdd() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		Genre found = genreService.getById(id);
		assertNotNull(found);
		assertEquals(genre, found);
	}

	@Test
	public void testList() throws MovieCatalogSystemException,
			CantGetEntityListException {

		assertNotNull(genreService.list());
		assertTrue(genreService.list().contains(genre));
	}

	@Test
	public void testGetById() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		Genre found = genreService.getById(id);
		assertNotNull(found);
		assertEquals(genre, found);
	}

	@Test
	public void testUpdate() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			EntityAlreadyExistsException, ValidationMovieCatalogException,
			CantUpdateEntityException {

		Genre found = genreService.getById(id);
		assertNotNull(found);
		assertEquals(genre, found);

		Genre genreNew = new Genre(id, "Kawabanga");
		genreService.update(genreNew);
		Genre foundNew = genreService.getById(id);
		assertNotNull(foundNew);
		assertEquals(genreNew, foundNew);
	}

	@Test
	public void testDelete() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			CantDeleteEntityException {

		assertNotNull(genreService.getById(id));
		genreService.delete(genre);
		assertNull(genreService.getById(id));
	}
}
