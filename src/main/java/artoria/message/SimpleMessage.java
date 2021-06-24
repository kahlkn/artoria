package artoria.message;

import artoria.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple message object.
 * @author Kahle
 */
public class SimpleMessage implements Message {
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private String messageId;
    private String destination;
    private Object body;

    @Override
    public String getMessageId() {

        return messageId;
    }

    @Override
    public void setMessageId(String messageId) {

        this.messageId = messageId;
    }

    @Override
    public String getDestination() {

        return destination;
    }

    @Override
    public void setDestination(String destination) {

        this.destination = destination;
    }

    @Override
    public Object getProperty(String propertyName) {
        Assert.notBlank(propertyName, "Parameter \"propertyName\" must not blank. ");
        return properties.get(propertyName);
    }

    @Override
    public void addProperty(String propertyName, Object propertyValue) {
        Assert.notBlank(propertyName, "Parameter \"propertyName\" must not blank. ");
        Assert.notNull(propertyValue, "Parameter \"propertyValue\" must not null. ");
        properties.put(propertyName, propertyValue);
    }

    @Override
    public void addProperties(Map<String, Object> properties) {
        Assert.notNull(properties, "Parameter \"properties\" must not null. ");
        this.properties.putAll(properties);
    }

    @Override
    public boolean containsProperty(String propertyName) {
        Assert.notBlank(propertyName, "Parameter \"propertyName\" must not blank. ");
        return properties.containsKey(propertyName);
    }

    @Override
    public void removeProperty(String propertyName) {
        Assert.notBlank(propertyName, "Parameter \"propertyName\" must not blank. ");
        properties.remove(propertyName);
    }

    @Override
    public Map<String, Object> getProperties() {

        return Collections.unmodifiableMap(properties);
    }

    @Override
    public void clearProperties() {

        properties.clear();
    }

    @Override
    public Object getBody() {

        return body;
    }

    @Override
    public void setBody(Object body) {

        this.body = body;
    }

}
