package net.muzichko.moviecatalog.exception;


public class ValidationMovieCatalogException extends MovieCatalogException {

    public ValidationMovieCatalogException() {
    }

    public ValidationMovieCatalogException(String message) {
        super(message);
    }

    public ValidationMovieCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationMovieCatalogException(Throwable cause) {
        super(cause);
    }

    public ValidationMovieCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
