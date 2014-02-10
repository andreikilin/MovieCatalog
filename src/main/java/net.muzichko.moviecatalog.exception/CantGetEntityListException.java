package net.muzichko.moviecatalog.exception;


public class CantGetEntityListException extends MovieCatalogException {

    public CantGetEntityListException() {
    }

    public CantGetEntityListException(String message) {
        super(message);
    }

    public CantGetEntityListException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantGetEntityListException(Throwable cause) {
        super(cause);
    }

    public CantGetEntityListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
