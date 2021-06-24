package artoria.message;

/**
 * Message listener.
 * @author Kahle
 */
public interface MessageListener {

    /**
     * Processing received messages.
     * @param message The received message
     */
    void onMessage(Message message);

}
