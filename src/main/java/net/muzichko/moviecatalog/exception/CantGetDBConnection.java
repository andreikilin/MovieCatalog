package net.muzichko.moviecatalog.exception;


public class CantGetDBConnection extends MovieCatalogSystemException {

    public CantGetDBConnection() {
    }

    public CantGetDBConnection(String message) {
        super(message);
    }

    public CantGetDBConnection(String message, Throwable cause) {
        super(message, cause);
    }

    public CantGetDBConnection(Throwable cause) {
        super(cause);
    }

    public CantGetDBConnection(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
