package artoria.message;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.CollectionUtils;
import artoria.util.MapUtils;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

class SimpleMessageDispatcher implements Runnable {
    private static Logger log = LoggerFactory.getLogger(SimpleMessageDispatcher.class);
    private final Map<String, LinkedList<MessageListener>> listenerMap;
    private final Map<String, BlockingQueue<Message>> topicMap;
    private final Map<String, BlockingQueue<Message>> queueMap;

    public SimpleMessageDispatcher(Map<String, LinkedList<MessageListener>> listenerMap,
                                   Map<String, BlockingQueue<Message>> topicMap,
                                   Map<String, BlockingQueue<Message>> queueMap) {
        this.listenerMap = listenerMap;
        this.topicMap = topicMap;
        this.queueMap = queueMap;
    }

    private void dispatchMessage(BlockingQueue<Message> messageQueue, LinkedList<MessageListener> listenerList) {
        Message message;
        while ((message = messageQueue.poll()) != null) {
            String destination = message.getDestination();
            destination = destination.toUpperCase();
            if (topicMap.get(destination) != null) {
                for (MessageListener messageListener : listenerList) {
                    consumeMessage(messageListener, message);
                }
            }
            else if (queueMap.get(destination) != null) {
                MessageListener messageListener = listenerList.removeFirst();
                consumeMessage(messageListener, message);
                listenerList.addLast(messageListener);
            }
            else {
                messageQueue.offer(message);
                throw new MessageException("Destination \"" + destination + "\" must exist. ");
            }
        }
    }

    private void consumeMessage(MessageListener listener, Message message) {
        try {
            listener.onMessage(message);
        }
        catch (Exception e) {
            log.info("Consume message error. ", e);
        }
    }

    private void dispatch(Map<String, BlockingQueue<Message>> map) {
        if (MapUtils.isEmpty(map)) { return; }
        for (Map.Entry<String, BlockingQueue<Message>> entry : map.entrySet()) {
            BlockingQueue<Message> messageQueue = entry.getValue();
            String destination = entry.getKey();
            if (CollectionUtils.isEmpty(messageQueue)) { continue; }
            LinkedList<MessageListener> listenerList = listenerMap.get(destination);
            if (CollectionUtils.isEmpty(listenerList)) { continue; }
            dispatchMessage(messageQueue, listenerList);
        }
    }

    @Override
    public void run() {
        try {
            dispatch(topicMap);
            dispatch(queueMap);
        }
        catch (Exception e) {
            log.info("Simple message dispatcher run error", e);
        }
    }

}
