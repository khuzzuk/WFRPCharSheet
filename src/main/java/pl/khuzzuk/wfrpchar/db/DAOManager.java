package pl.khuzzuk.wfrpchar.db;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Component
@Named("daoManager")
public class DAOManager {
    @Inject
    private SessionFactory factory;

    public Session openNewSession() {
        return factory.openSession();
    }
}
