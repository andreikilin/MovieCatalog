package net.muzichko.moviecatalog.domain;


import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenreValidator {

    private final static String TEXT_PATTERN = "[a-zA-Zа-яА-Я]{0,200}";

    static Logger log = Logger.getLogger(GenreValidator.class);

    public static void validate(Genre genre) throws ValidationMovieCatalogException {

        LinkedList<String> errorList = new LinkedList<>();

        if (genre == null) {
            errorList.add("Null pointer genre");
        } else {
            Pattern pattern = Pattern.compile(TEXT_PATTERN);
            Matcher matcher = pattern.matcher(genre.getName());
            if (!matcher.matches()) {
                errorList.add("Genre's name is not valid. It must match the pattern " + TEXT_PATTERN);
            }
        }

        if(!errorList.isEmpty()){
            String message = "Genre validation failed: \n";
            for(String err : errorList){
                message += err + "\n";
            }

            log.error(message);
            throw new ValidationMovieCatalogException(message);
        }
    }

}
