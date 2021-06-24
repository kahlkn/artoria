package artoria.message;

import java.io.Serializable;
import java.util.Map;

/**
 * Provide a high level abstract message object for message queue.
 * @see <a href="https://en.wikipedia.org/wiki/Message_queue">Message queue</a>
 * @author Kahle
 */
public interface Message extends Serializable {

    /**
     * Get the message id.
     * @return The message id
     */
    String getMessageId();

    /**
     * Set the message id.
     * @param messageId The message id
     */
    void setMessageId(String messageId);

    /**
     * Get the destination.
     * @return The destination the message is expected to arrive at
     */
    String getDestination();

    /**
     * Set the destination.
     * @param destination The destination the message is expected to arrive at
     */
    void setDestination(String destination);

    /**
     * Get property value by property name.
     * @param propertyName The property name
     * @return The property value
     */
    Object getProperty(String propertyName);

    /**
     * Add a property.
     * @param propertyName The property name
     * @param propertyValue The property value
     */
    void addProperty(String propertyName, Object propertyValue);

    /**
     * Batch add the properties.
     * @param properties The properties
     */
    void addProperties(Map<String, Object> properties);

    /**
     * Contain the property name.
     * @param propertyName The property name
     * @return Contain result
     */
    boolean containsProperty(String propertyName);

    /**
     * Remove property value by property name.
     * @param propertyName The property name
     */
    void removeProperty(String propertyName);

    /**
     * Get the properties.
     * @return The properties
     */
    Map<String, Object> getProperties();

    /**
     * Clear all properties.
     */
    void clearProperties();

    /**
     * Get the message body.
     * @return The message body
     */
    Object getBody();

    /**
     * Set the message body.
     * @param body The message body
     */
    void setBody(Object body);

}
