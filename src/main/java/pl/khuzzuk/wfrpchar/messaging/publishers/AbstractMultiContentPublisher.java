package pl.khuzzuk.wfrpchar.messaging.publishers;

import pl.khuzzuk.wfrpchar.messaging.ContentMessage;

public abstract class AbstractMultiContentPublisher
        extends AbstractBagPublisher<Object> implements MultiContentPublisher {
    @SuppressWarnings("unchecked")
    @Override
    public void publish(Object content, String msgType) {
        getBus().publish(new ContentMessage().setMessage(content).setType(msgType));
    }
}
