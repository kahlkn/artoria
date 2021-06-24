package artoria.message;

import artoria.common.AsyncCallback;
import artoria.identifier.IdentifierUtils;
import artoria.lifecycle.LifecycleException;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.thread.SimpleThreadFactory;
import artoria.util.Assert;
import artoria.util.ShutdownHookUtils;
import artoria.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static artoria.common.Constants.TWO;
import static artoria.common.Constants.ZERO;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Simple message provider.
 * @author Kahle
 */
public class SimpleMessageProvider implements MessageProvider {
    private final Map<String, LinkedList<MessageListener>> listenerMap = new ConcurrentHashMap<String, LinkedList<MessageListener>>();
    private final Map<String, BlockingQueue<Message>> topicMap = new ConcurrentHashMap<String, BlockingQueue<Message>>();
    private final Map<String, BlockingQueue<Message>> queueMap = new ConcurrentHashMap<String, BlockingQueue<Message>>();
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private static Logger log = LoggerFactory.getLogger(SimpleMessageProvider.class);
    private static final String THREAD_NAME_PREFIX = "simple-message-provider-executor";
    private static final int DEFAULT_CORE_POOL_SIZE = TWO;
    private static final int DEFAULT_PERIOD = 5000;

    public SimpleMessageProvider() {

        this(DEFAULT_CORE_POOL_SIZE, DEFAULT_PERIOD);
    }

    public SimpleMessageProvider(int corePoolSize, long period) {
        this(
                new ScheduledThreadPoolExecutor(
                        corePoolSize,
                        new SimpleThreadFactory(THREAD_NAME_PREFIX, TRUE)
                ),
                period
        );
    }

    public SimpleMessageProvider(ScheduledThreadPoolExecutor threadPool, long period) {
        Assert.notNull(threadPool, "Parameter \"threadPool\" must not null. ");
        Assert.isTrue(period > ZERO, "Parameter \"period\" must greater than 0. ");
        SimpleMessageDispatcher messageDispatcher = new SimpleMessageDispatcher(listenerMap, topicMap, queueMap);
        threadPool.scheduleAtFixedRate(messageDispatcher, period, period, MILLISECONDS);
        ShutdownHookUtils.addExecutorService(threadPool);
        this.scheduledThreadPoolExecutor = threadPool;
    }

    private BlockingQueue<Message> messageQueue(String destination) {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        destination = destination.toUpperCase();
        BlockingQueue<Message> messageQueue;
        if ((messageQueue = topicMap.get(destination)) != null
                || (messageQueue = queueMap.get(destination)) != null) {
            return messageQueue;
        }
        else {
            throw new MessageException("Destination \"" + destination + "\" must exist. ");
        }
    }

    public void createQueue(String destination) {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
        destination = destination.toUpperCase();
        queueMap.put(destination, messageQueue);
    }

    public void createTopic(String destination) {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
        destination = destination.toUpperCase();
        topicMap.put(destination, messageQueue);
    }

    @Override
    public void initialize() throws LifecycleException {

    }

    @Override
    public void listening(String destination, Map<String, Object> parameters, MessageListener listener) throws MessageException {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        Assert.notNull(listener, "Parameter \"listener\" must not null. ");
        destination = destination.toUpperCase();
        LinkedList<MessageListener> listenerList = listenerMap.get(destination);
        if (listenerList == null) {
            listenerList = new LinkedList<MessageListener>();
            listenerMap.put(destination, listenerList);
        }
        listenerList.add(listener);
    }

    @Override
    public void removeListening(String destination, Map<String, Object> parameters, MessageListener listener) throws MessageException {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        Assert.notNull(listener, "Parameter \"listener\" must not null. ");
        destination = destination.toUpperCase();
        List<MessageListener> listenerList = listenerMap.get(destination);
        if (listenerList == null) { return; }
        listenerList.remove(listener);
    }

    @Override
    public void send(Message message) throws MessageException {
        Assert.notNull(message, "Parameter \"message\" must not null. ");
        String destination = message.getDestination();
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        String messageId = message.getMessageId();
        if (StringUtils.isBlank(messageId)) {
            messageId = IdentifierUtils.nextStringIdentifier();
            message.setMessageId(messageId);
        }
        destination = destination.toUpperCase();
        messageQueue(destination).offer(message);
    }

    @Override
    public void sendAsync(Message message, AsyncCallback<Object> callback) throws MessageException {
        Assert.notNull(message, "Parameter \"message\" must not null. ");
        Assert.notNull(callback, "Parameter \"callback\" must not null. ");
        String destination = message.getDestination();
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        Runnable runnable = new SimpleAsyncSender(this, message, callback);
        scheduledThreadPoolExecutor.execute(runnable);
    }

    @Override
    public Message receive(String destination, Map<String, Object> parameters) throws MessageException {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        destination = destination.toUpperCase();
        return messageQueue(destination).poll();
    }

    @Override
    public Message receive(String destination, Map<String, Object> parameters, long timeoutMillis) throws MessageException {
        Assert.notBlank(destination, "Parameter \"destination\" must not blank. ");
        try {
            destination = destination.toUpperCase();
            return messageQueue(destination).take();
        }
        catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public void destroy() throws Exception {
        if (scheduledThreadPoolExecutor != null
                && !scheduledThreadPoolExecutor.isShutdown()) {
            scheduledThreadPoolExecutor.shutdown();
            ShutdownHookUtils.removeExecutorService(scheduledThreadPoolExecutor);
        }
    }

}
