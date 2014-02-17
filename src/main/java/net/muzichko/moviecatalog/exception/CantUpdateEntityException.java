package net.muzichko.moviecatalog.exception;

public class CantUpdateEntityException extends MovieCatalogException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantUpdateEntityException() {
    }

    public CantUpdateEntityException(String message) {
        super(message);
    }

    public CantUpdateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantUpdateEntityException(Throwable cause) {
        super(cause);
    }

    public CantUpdateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
