package net.muzichko.moviecatalog.domain;


import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewValidator {

    private final static String COMMENT_PATTERN = "[a-zA-Zа-яА-Я]{0,200}";

    static Logger log = Logger.getLogger(ReviewValidator.class);

    public static void validate(Review review) throws ValidationMovieCatalogException {

        LinkedList<String> errorList = new LinkedList<>();

        if (review == null) {
            errorList.add("Null pointer review");
        } else {

            if(review.getUser() == null){
                errorList.add("Null pointer user");
            }

            if(review.getMovie() == null){
                errorList.add("Null pointer movie");
            }

            Pattern pattern = Pattern.compile(COMMENT_PATTERN);
            Matcher matcher = pattern.matcher(review.getComment());
            if (!matcher.matches()) {
                errorList.add("Comment is not valid. It must match the pattern " + COMMENT_PATTERN);
            }

            if((review.getRating() < 0) || (review.getRating() > 100)){
                errorList.add("Rating is not valid. Expected range: 0-100");
            }

        }

        if(!errorList.isEmpty()){
            String message = "Review validation failed: \n";
            for(String err : errorList){
                message += err + "\n";
            }

            log.error(message);
            throw new ValidationMovieCatalogException(message);
        }
    }

}
