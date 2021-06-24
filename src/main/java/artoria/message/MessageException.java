package artoria.message;

import artoria.exception.UncheckedException;

/**
 * Message exception.
 * @author Kahle
 */
public class MessageException extends UncheckedException {

    public MessageException() {

        super();
    }

    public MessageException(String message) {

        super(message);
    }

    public MessageException(Throwable cause) {

        super(cause);
    }

    public MessageException(String message, Throwable cause) {

        super(message, cause);
    }

}
