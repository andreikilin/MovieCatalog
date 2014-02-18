package net.muzichko.moviecatalog.exception;


public class UnablePerformDBOperation extends MovieCatalogSystemException {

    public UnablePerformDBOperation() {
    }

    public UnablePerformDBOperation(String message) {
        super(message);
    }

    public UnablePerformDBOperation(String message, Throwable cause) {
        super(message, cause);
    }

    public UnablePerformDBOperation(Throwable cause) {
        super(cause);
    }

    public UnablePerformDBOperation(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
