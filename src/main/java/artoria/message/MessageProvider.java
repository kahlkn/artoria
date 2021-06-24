package artoria.message;

import artoria.common.AsyncCallback;
import artoria.lifecycle.Destroyable;
import artoria.lifecycle.Initializable;

import java.util.Map;

/**
 * Message provider.
 * @author Kahle
 */
public interface MessageProvider extends Initializable, Destroyable {

    /**
     * Listens for the specified queue.
     * @param destination The destination the message is expected to arrive at
     * @param parameters Supplement any fields that may appear that need to be passed
     * @param listener The message listener
     * @throws MessageException An error related to the message occurred
     */
    void listening(String destination, Map<String, Object> parameters, MessageListener listener) throws MessageException;

    /**
     * Deletes the listener on the specified queue.
     * @param destination The destination the message is expected to arrive at
     * @param parameters Supplement any fields that may appear that need to be passed
     * @param listener The message listener
     * @throws MessageException An error related to the message occurred
     */
    void removeListening(String destination, Map<String, Object> parameters, MessageListener listener) throws MessageException;

    /**
     * Send message.
     * @param message The message to be sent
     * @throws MessageException An error related to the message occurred
     */
    void send(Message message) throws MessageException;

    /**
     * Send message asynchronously.
     * @param message The message to be sent
     * @param callback The callback object for asynchronous invocation
     * @throws MessageException An error related to the message occurred
     */
    void sendAsync(Message message, AsyncCallback<Object> callback) throws MessageException;

    /**
     * Receive a message from a specific queue, if there is no message, null is immediately returned.
     * @param destination The destination the message is expected to arrive at
     * @param parameters Supplement any fields that may appear that need to be passed
     * @return The received message
     * @throws MessageException An error related to the message occurred
     */
    Message receive(String destination, Map<String, Object> parameters) throws MessageException;

    /**
     * Receive a message from a specific queue, if there is no message, wait for some time.
     * @param destination The destination the message is expected to arrive at
     * @param parameters Supplement any fields that may appear that need to be passed
     * @param timeoutMillis How long to wait before giving up
     * @return The received message
     * @throws MessageException An error related to the message occurred
     */
    Message receive(String destination, Map<String, Object> parameters, long timeoutMillis) throws MessageException;

}
