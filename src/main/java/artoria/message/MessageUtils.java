package artoria.message;

import artoria.common.AsyncCallback;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.Assert;
import artoria.util.MapUtils;

import java.util.Map;

/**
 * Message tools.
 * @author Kahle
 */
public class MessageUtils {
    private static Logger log = LoggerFactory.getLogger(MessageUtils.class);
    private static MessageProvider messageProvider;

    public static MessageProvider getMessageProvider() {
        if (messageProvider != null) { return messageProvider; }
        synchronized (MessageUtils.class) {
            if (messageProvider != null) { return messageProvider; }
            MessageUtils.setMessageProvider(new SimpleMessageProvider());
            return messageProvider;
        }
    }

    public static void setMessageProvider(MessageProvider messageProvider) {
        Assert.notNull(messageProvider, "Parameter \"messageProvider\" must not null. ");
        log.info("Set message provider: {}", messageProvider.getClass().getName());
        MessageUtils.messageProvider = messageProvider;
    }

    public static void listening(String destination, Map<String, Object> parameters, MessageListener listener) {

        getMessageProvider().listening(destination, parameters, listener);
    }

    public static void removeListening(String destination, Map<String, Object> parameters, MessageListener listener) {

        getMessageProvider().removeListening(destination, parameters, listener);
    }

    public static void send(Message message) {

        getMessageProvider().send(message);
    }

    public static void send(String destination, Object body) {

        send(destination, null, body);
    }

    public static void send(String destination, Map<String, Object> properties, Object body) {
        Message message = new SimpleMessage();
        message.setDestination(destination);
        if (MapUtils.isNotEmpty(properties)) {
            message.addProperties(properties);
        }
        message.setBody(body);
        getMessageProvider().send(message);
    }

    public static void sendAsync(Message message, AsyncCallback<Object> callback) {

        getMessageProvider().sendAsync(message, callback);
    }

    public static Message receive(String destination, Map<String, Object> parameters) {

        return getMessageProvider().receive(destination, parameters);
    }

    public static Message receive(String destination, Map<String, Object> parameters, long timeoutMillis) {

        return getMessageProvider().receive(destination, parameters, timeoutMillis);
    }

}
