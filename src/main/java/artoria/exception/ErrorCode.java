package artoria.exception;

/**
 * The error code.
 * @see <a href="https://en.wikipedia.org/wiki/Error_code">Error code</a>
 * @author Kahle
 */
public interface ErrorCode {

    /**
     * The code of the error.
     * @return The code
     */
    String getCode();

    /**
     * The description of the error.
     * @return The description
     */
    String getDescription();

}
