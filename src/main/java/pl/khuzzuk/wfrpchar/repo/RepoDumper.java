package pl.khuzzuk.wfrpchar.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    @Autowired
    private Repository repository;

    @PostConstruct
    private void init() {
        bus.setReaction(messages.getProperty("database.change"), this::save);
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("repository1.json"))) {
            bufferedWriter.write(mapper.writeValueAsString(repository));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
