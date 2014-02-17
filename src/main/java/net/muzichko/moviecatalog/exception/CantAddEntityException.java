package net.muzichko.moviecatalog.exception;


public class CantAddEntityException extends MovieCatalogException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantAddEntityException() {
    }

    public CantAddEntityException(String message) {
        super(message);
    }

    public CantAddEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantAddEntityException(Throwable cause) {
        super(cause);
    }

    public CantAddEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
