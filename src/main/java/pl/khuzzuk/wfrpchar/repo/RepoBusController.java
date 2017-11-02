package pl.khuzzuk.wfrpchar.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Collection;
import java.util.Properties;

@Component
public class RepoBusController {
    @Autowired
    private RepoQueryResolver repoQueryResolver;
    @Autowired
    private Bus bus;
    @Autowired
    @Named("messages")
    private Properties messages;

    @PostConstruct
    private void init() {
        bus.<Class<?>, Collection<?>>setResponse(messages.getProperty("database.getAll"), repoQueryResolver::get);
        bus.<Criteria, pl.khuzzuk.wfrpchar.entities.Named<String>>setResponse(messages.getProperty("database.get.named"), repoQueryResolver::get);
    }
}
