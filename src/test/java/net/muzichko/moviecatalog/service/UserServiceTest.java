package net.muzichko.moviecatalog.service;

import static org.junit.Assert.*;
import net.muzichko.moviecatalog.domain.User;
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
public class UserServiceTest {

	@Autowired
	UserService userService;

	private int id = 1;
	private String login = "testUser";
	private String password = "testPassword";
	private String email = "testAddress@domain.com";
	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User(id, login, password, email);
		userService.add(user);
	}

	@After
	public void tearDown() throws Exception {
		User user = userService.getById(id);
		if (user != null)
			userService.delete(user);
	}

	@Test
	public void testAdd() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		User found = userService.getById(id);
		assertNotNull(found);
		assertEquals(found, user);
	}

	@Test
	public void testList() throws MovieCatalogSystemException,
			CantGetEntityListException {

		assertNotNull(userService.list());
		assertTrue(userService.list().contains(user));
	}

	@Test
	public void testGetById() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException {

		User found = userService.getById(id);
		assertNotNull(found);
		assertEquals(found, user);
	}

	@Test
	public void testUpdate() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			EntityAlreadyExistsException, ValidationMovieCatalogException,
			CantUpdateEntityException {

		User found = userService.getById(id);
		assertNotNull(found);
		assertEquals(found, user);

		User userNew = new User(id, login, "updatedPassword", email);
		userService.update(userNew);
		User foundNew = userService.getById(id);
		assertNotNull(foundNew);
		assertEquals(foundNew, userNew);
	}

	@Test
	public void testDelete() throws NoSuchEntityException,
			MovieCatalogSystemException, CantGetEntityListException,
			CantDeleteEntityException {

		assertNotNull(userService.getById(id));
		userService.delete(user);
		assertNull(userService.getById(id));
	}

}
