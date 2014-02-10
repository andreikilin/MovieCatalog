package net.muzichko.moviecatalog.test;

import net.muzichko.moviecatalog.domain.User;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static net.muzichko.moviecatalog.test.Utils.*;

public class UserTest {

    private static ApplicationContext context;
    private static UserService userService;

    @Before
    public void setUp() {


        context = new ClassPathXmlApplicationContext("context-test.xml");
        userService = (UserService) context.getBean("UserService");

    }

    @Test
    public void testAddNewUniqueUser() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(20), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        User userFromDb = userService.getById(user.getId());

        if (!user.equals(userFromDb)) {
            throw new AssertionError("New user isn't equal to new user from DB.");
        }
    }

    @Test(expected = ValidationMovieCatalogException.class)
    public void testAddNewUserInvalidCharactersLogin() throws MovieCatalogException {

        User user = new User(randomCyrillicSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

    }

    @Test(expected = ValidationMovieCatalogException.class)
    public void testAddNewUserInvalidCharactersPassword() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomCyrillicSymbolString(10), randomEmail());
        userService.add(user);

    }

    @Test(expected = ValidationMovieCatalogException.class)
    public void testAddNewUserInvalidCharactersEmail() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomCyrillicSymbolString(10),
                randomCyrillicSymbolString(10));
        userService.add(user);

    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testAddNewExistingLoginUser() throws MovieCatalogException {

        User user = new User("admin", randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testAddNewExistingEmailUser() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), "muzichko_vika@mail.ru");
        userService.add(user);

    }

    @Test
    public void DeleteExistingUser() throws MovieCatalogException {

        User user = new User("test", randomEnglishSymbolString(10), randomEmail());
        userService.add(user);
        userService.delete(user);

    }

    @Test(expected = CantDeleteEntityException.class)
    public void DeleteNotExistingUser() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.delete(user);

    }

    @Test
    public void UpdateExistingUserUniqueLogin() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        user.setLogin(randomEnglishSymbolString(10));

        userService.update(user);

    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void UpdateExistingUserNotUniqueLogin() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        user.setLogin("admin");

        userService.update(user);

    }

    @Test
    public void UpdateExistingUserUniqueEmail() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        user.setEmail(randomEmail());

//        boolean alive = TransactionSynchronizationManager.isActualTransactionActive();

        userService.update(user);

    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void UpdateExistingUserNotUniqueEmail() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());
        userService.add(user);

        user.setEmail("muzichko_vika@mail.ru");

        userService.update(user);

    }

    @Test(expected = CantUpdateEntityException.class)
    public void UpdateNotExistingUser() throws MovieCatalogException {

        User user = new User(randomEnglishSymbolString(10), randomEnglishSymbolString(10), randomEmail());

        userService.update(user);

    }

}
