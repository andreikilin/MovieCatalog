package net.muzichko.moviecatalog.service;

import static org.junit.Assert.*;
import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.exception.CantDeleteEntityException;
import net.muzichko.moviecatalog.exception.CantGetEntityListException;
import net.muzichko.moviecatalog.exception.CantUpdateEntityException;
import net.muzichko.moviecatalog.exception.EntityAlreadyExistsException;
import net.muzichko.moviecatalog.exception.MovieCatalogSystemException;
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
public class CountryServiceTest {

	@Autowired
	CountryService countryService;

	private int id = 100;
	private final String name = "Madagaskar";
	private Country country;

	@Before
	public void setUp() throws Exception {
		country = new Country(id, name);
		countryService.add(country);

	}

	@After
	public void tearDown() throws Exception {
		Country country = countryService.getById(id);
		if (country != null)
			countryService.delete(country);
	}

	@Test
	public void tetsAdd() throws MovieCatalogSystemException,
			CantGetEntityListException {

		Country found = countryService.getById(id);
		assertNotNull(found);
		assertEquals(country, found);

	}

	@Test
	public void tetsList() throws MovieCatalogSystemException,
			CantGetEntityListException {

		assertNotNull(countryService.list());
		assertTrue(countryService.list().contains(country));
	}

	@Test
	public void testGetById() throws MovieCatalogSystemException,
			CantGetEntityListException {

		Country found = countryService.getById(id);
		assertNotNull(found);
		assertEquals(country, found);
	}

	@Test
	public void tetsUpdate() throws MovieCatalogSystemException,
			CantGetEntityListException, EntityAlreadyExistsException,
			ValidationMovieCatalogException, CantUpdateEntityException {

		Country found = countryService.getById(id);
		assertNotNull(found);
		assertEquals(country, found);

		Country countryNew = new Country(id, "Karaganda");
		countryService.update(countryNew);
		Country foundNew = countryService.getById(id);
		assertNotNull(foundNew);
		assertEquals(countryNew, foundNew);
	}

	@Test
	public void testDelete() throws MovieCatalogSystemException,
			CantGetEntityListException, CantDeleteEntityException {

		assertNotNull(countryService.getById(id));
		countryService.delete(country);
		assertNull(countryService.getById(id));
	}
}
