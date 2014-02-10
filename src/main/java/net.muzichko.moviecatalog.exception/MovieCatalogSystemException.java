package net.muzichko.moviecatalog.exception;

public class MovieCatalogSystemException extends MovieCatalogException {

    public MovieCatalogSystemException() {
    }

    public MovieCatalogSystemException(String message) {
        super(message);
    }

    public MovieCatalogSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieCatalogSystemException(Throwable cause) {
        super(cause);
    }

    public MovieCatalogSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
