package net.muzichko.moviecatalog.domain;


import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryValidator {

    private final static String COUNTRY_PATTERN = "[a-zA-Zа-яА-Я]{0,200}";

    static Logger log = Logger.getLogger(GenreValidator.class);

    public static void validate(Country country) throws ValidationMovieCatalogException {

        LinkedList<String> errorList = new LinkedList<>();

        if (country == null) {
            errorList.add("Null pointer country");
        } else {
            Pattern pattern = Pattern.compile(COUNTRY_PATTERN);
            Matcher matcher = pattern.matcher(country.getName());
            if (!matcher.matches()) {
                errorList.add("Country's name is not valid. It must match the pattern " + COUNTRY_PATTERN);
            }
        }

        if(!errorList.isEmpty()){
            String message = "Country validation failed: \n";
            for(String err : errorList){
                message += err + "\n";
            }

            log.error(message);
            throw new ValidationMovieCatalogException(message);
        }
    }

}
