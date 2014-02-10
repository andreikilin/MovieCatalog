package net.muzichko.moviecatalog.domain;


import net.muzichko.moviecatalog.exception.ValidationMovieCatalogException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private final static String LOGIN_PATTERN = "[a-zA-Z0-9]{0,100}";
    private final static String PASSWORD_PATTERN = "[a-zA-Z0-9]{0,10}";
    private final static String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";

    static Logger log = Logger.getLogger(GenreValidator.class);

    public static void validate(User user) throws ValidationMovieCatalogException {

        LinkedList<String> errorList = new LinkedList<>();

        if (user == null) {
            errorList.add("Null pointer user");
        } else {
            Pattern pattern = Pattern.compile(LOGIN_PATTERN);
            Matcher matcher = pattern.matcher(user.getLogin());
            if (!matcher.matches()) {
                errorList.add("User's login is not valid. It must match the pattern " + LOGIN_PATTERN);
            }

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(user.getPassword());
            if (!matcher.matches()) {
                errorList.add("User's password is not valid. It must match the pattern " + PASSWORD_PATTERN);
            }

            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(user.getEmail());
            if (!matcher.matches()) {
                errorList.add("User's email is not valid. It must match the pattern " + EMAIL_PATTERN);
            }
        }

        if(!errorList.isEmpty()){
            String message = "User validation failed: \n";
            for(String err : errorList){
                message += err + "\n";
            }

            log.error(message);
            throw new ValidationMovieCatalogException(message);
        }
    }

}
