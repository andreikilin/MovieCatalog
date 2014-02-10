package net.muzichko.moviecatalog.domain;

import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieValidator {

    private final static String TEXT_PATTERN = "[a-zA-Zа-яА-Я0-9]{0,200}";

    static Logger log = Logger.getLogger(MovieValidator.class);

    public static void validate(Movie movie) throws ValidationMovieCatalogException {

        LinkedList<String> errorList = new LinkedList<>();

        if (movie == null) {
            errorList.add("Null pointer movie");
        } else {

            if(movie.getGenre() == null){
                errorList.add("Null pointer genre");
            }

            if(movie.getCountry() == null){
                errorList.add("Null pointer country");
            }

            errorList = checkYear(movie, errorList);

            // name, description, starring validate with TEXT_PATTERN
            errorList = checkStringFields(movie, errorList);
        }

        if(!errorList.isEmpty()){
            String message = "Movie validation failed: \n";
            for(String err : errorList){
                message += err + "\n";
            }

            log.error(message);
            throw new ValidationMovieCatalogException(message);
        }
    }


    private static LinkedList<String> checkYear(Movie movie, LinkedList<String> errorList) {

        int year = movie.getYear();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        if((year < 1900) || (year > (currentYear + 1))){
            errorList.add("Year is not valid, expected range: 1900 - (current year + 1).");
        }

        return errorList;
    }

    private static LinkedList<String> checkStringFields(Movie movie, LinkedList<String> errorList) {

        if (!movie.getName().isEmpty()) {
            Pattern pattern = Pattern.compile(TEXT_PATTERN);
            Matcher matcher = pattern.matcher(movie.getName());
            if (!matcher.matches()) {
                errorList.add("Name is not valid. It must match the pattern [a-zA-Zа-яА-Я0-9]{0,200}");
            }
        }

        if (!movie.getDescription().isEmpty()) {
            Pattern pattern = Pattern.compile(TEXT_PATTERN);
            Matcher matcher = pattern.matcher(movie.getDescription());
            if (!matcher.matches()) {
                errorList.add("Description not valid. It must match the pattern [a-zA-Zа-яА-Я0-9]{0,200}");
            }
        }

        if (!movie.getStarring().isEmpty()) {
            Pattern pattern = Pattern.compile(TEXT_PATTERN);
            Matcher matcher = pattern.matcher(movie.getStarring());
            if (!matcher.matches()) {
                errorList.add("Starring not valid. It must match the pattern [a-zA-Zа-яА-Я0-9]{0,200}");
            }
        }

        return errorList;
    }

}
