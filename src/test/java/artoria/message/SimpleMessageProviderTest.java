package artoria.message;

import artoria.common.AsyncCallback;
import artoria.util.ThreadUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class SimpleMessageProviderTest {
    private static SimpleMessageProvider messageProvider = new SimpleMessageProvider();
    private static MessageListener messageListener1;
    private static MessageListener messageListener2;
    private static AsyncCallback<Object> callback;
    private static String destination = "message.test";

    static {
//        messageProvider.createQueue(destination);
        messageProvider.createTopic(destination);
        messageListener1 = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(">>listener 1 receive: " + JSON.toJSONString(message));
            }
        };
        messageListener2 = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(">>listener 2 receive: " + JSON.toJSONString(message));
            }
        };
        callback = new AsyncCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("onSuccess");
            }
            @Override
            public void onFailure(Throwable th) {
                System.out.println("onFailure");
            }
        };
    }

    @Test
    public void test1() {
        messageProvider.listening(destination, null, messageListener1);
        messageProvider.listening(destination, null, messageListener2);
        for (int i = 0; i < 10; i++) {
            Message message = new SimpleMessage();
            message.setDestination(destination);
            message.setBody("test1 >> " + i);
            messageProvider.send(message);
        }
        ThreadUtils.sleepQuietly(9000L);
    }

    @Test
    public void test2() {
        messageProvider.listening(destination, null, messageListener1);
        messageProvider.listening(destination, null, messageListener2);
        Message message = new SimpleMessage();
        message.setDestination(destination);
        message.setBody("test2 >> sendAsync");
        messageProvider.sendAsync(message, callback);
        System.out.println("sendAsync end");
        ThreadUtils.sleepQuietly(9000L);
    }

}
