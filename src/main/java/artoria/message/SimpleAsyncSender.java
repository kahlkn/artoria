package artoria.message;

import artoria.common.AsyncCallback;
import artoria.util.Assert;

public class SimpleAsyncSender implements Runnable {
    private MessageProvider messageProvider;
    private AsyncCallback<Object> callback;
    private Message message;

    public SimpleAsyncSender(MessageProvider messageProvider, Message message, AsyncCallback<Object> callback) {
        Assert.notNull(messageProvider, "Parameter \"messageProvider\" must not null. ");
        Assert.notNull(callback, "Parameter \"callback\" must not null. ");
        Assert.notNull(message, "Parameter \"message\" must not null. ");
        this.messageProvider = messageProvider;
        this.callback = callback;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            messageProvider.send(message);
            callback.onSuccess(message);
        }
        catch (Exception e) {
            callback.onFailure(e);
        }
    }

}
