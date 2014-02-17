package net.muzichko.moviecatalog.exception;


public class MovieCatalogException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovieCatalogException() {
    }

    public MovieCatalogException(String message) {
        super(message);
    }

    public MovieCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieCatalogException(Throwable cause) {
        super(cause);
    }

    public MovieCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
