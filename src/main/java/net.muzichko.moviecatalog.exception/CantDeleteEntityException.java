package net.muzichko.moviecatalog.exception;

public class CantDeleteEntityException extends MovieCatalogException {

    public CantDeleteEntityException() {
    }

    public CantDeleteEntityException(String message) {
        super(message);
    }

    public CantDeleteEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantDeleteEntityException(Throwable cause) {
        super(cause);
    }

    public CantDeleteEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
