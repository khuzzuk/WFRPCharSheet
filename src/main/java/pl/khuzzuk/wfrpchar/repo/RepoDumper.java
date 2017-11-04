package pl.khuzzuk.wfrpchar.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Properties;

@Component
public class RepoDumper {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Bus bus;
    @Autowired
    @Named("messages")
    private Properties messages;

    @PostConstruct
    private void init() {
        bus.setReaction(messages.getProperty("database.save"), this::save);
    }

    private void save() {
    }
}
