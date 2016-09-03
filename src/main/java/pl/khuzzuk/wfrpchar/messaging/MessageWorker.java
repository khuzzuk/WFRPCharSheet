package pl.khuzzuk.wfrpchar.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@Component
@Log4j2(topic = "MessageLogger")
public class MessageWorker {
    private MultiValuedMap<String, Subscriber<? extends Message>> subscribers;
    private BlockingQueue<Message> channel;
    private ExecutorService pool;

    @Inject
    public MessageWorker(@Subscribers MultiValuedMap<String, Subscriber<? extends Message>> subscribers,
                         @Channel BlockingQueue<Message> channel,
                         @Named("pool") ExecutorService pool) {
        this.subscribers = subscribers;
        this.channel = channel;
        this.pool = pool;
    }

    @PostConstruct
    private void startWorker() {
        Thread schedulerThread = new Thread(new Scheduler());
        schedulerThread.setName("Scheduler thread");
        schedulerThread.start();
    }

    private class Scheduler implements Runnable {

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            while (true) {
                Message message = getMessage();
                if (message == null) {
                    continue;
                }
                Collection<Subscriber<? extends Message>> subscriberCollection =
                        subscribers.get(message.getType());
                for (Subscriber s : subscriberCollection) {
                    log.info("forwarded: " + message + " to: " + s);
                    pool.submit(new MessageTask(message, s));
                }
            }
        }

        private Message getMessage() {
            try {
                return channel.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @AllArgsConstructor
    private class MessageTask implements Runnable {
        private Message message;
        private Subscriber subscriber;

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            try {
                log.info("received message: " + message);
                subscriber.receive(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
