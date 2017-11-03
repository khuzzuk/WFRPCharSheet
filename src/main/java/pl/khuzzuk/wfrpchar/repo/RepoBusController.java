package pl.khuzzuk.wfrpchar.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.Named;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Component
public class RepoBusController {
    @Autowired
    private RepoQueryResolver repoQueryResolver;
    @Autowired
    private Bus bus;
    @Autowired
    @javax.inject.Named("messages")
    private Properties messages;

    @PostConstruct
    private void init() {
        bus.<Class<?>, Collection<?>>setResponse(messages.getProperty("database.getAll"), repoQueryResolver::get);
        bus.<Criteria, Named<String>>setResponse(messages.getProperty("database.get.named"), repoQueryResolver::get);
        bus.<Criteria, List<?>>setResponse(messages.getProperty("database.remove"), criteria -> {
            repoQueryResolver.remove(criteria);
            return repoQueryResolver.get(criteria.getType());
        });
    }
}
