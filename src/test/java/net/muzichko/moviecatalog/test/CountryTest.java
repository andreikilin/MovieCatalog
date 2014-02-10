package net.muzichko.moviecatalog.test;


import net.muzichko.moviecatalog.domain.Country;
import net.muzichko.moviecatalog.exception.*;
import net.muzichko.moviecatalog.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CountryTest {

    private ApplicationContext context;
    private CountryService countryService;

    @Before
    public void setUp(){

        context = new ClassPathXmlApplicationContext("context-test.xml");
        countryService = (CountryService) context.getBean("CountryService");

    }

    @Test
    public void testAddNewUniqueCountry() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        countryService.add(country);

        Country countryFromDb = countryService.getById(country.getId());

        if(!country.equals(countryFromDb)){
            throw new AssertionError("New country isn't equal to new country from DB.");
        }

    }

    @Test (expected = ValidationMovieCatalogException.class)
    public void testAddNewCountryInvalidCharacters() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolNumbersString(20));
        countryService.add(country);

    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testAddNewExistingCountry() throws MovieCatalogException {

        Country country = new Country("USA");
        countryService.add(country);

    }

    @Test
    public void testDeleteExistingCountry() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        countryService.add(country);
        countryService.delete(country);

    }

    @Test (expected = CantDeleteEntityException.class)
    public void testDeleteNotExistingCountry() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        countryService.delete(country);

    }

    @Test
    public void testUpdateExistingCountryUniqueName() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        countryService.add(country);

        country.setName(Utils.randomEnglishSymbolString(20));

        countryService.update(country);

    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void testUpdateExistingCountryNotUniqueName() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        countryService.add(country);

        country.setName("USA");

        countryService.update(country);

    }

    @Test (expected = CantUpdateEntityException.class)
    public void testUpdateNotExistingCountry() throws MovieCatalogException {

        Country country = new Country(Utils.randomEnglishSymbolString(20));
        country.setName(Utils.randomEnglishSymbolString(20));

        countryService.update(country);

    }

}
