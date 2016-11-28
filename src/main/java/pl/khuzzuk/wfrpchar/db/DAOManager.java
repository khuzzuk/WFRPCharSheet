package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.db.annot.Professions;
import pl.khuzzuk.wfrpchar.db.annot.Races;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;

import javax.inject.Inject;

@Component
@Manager
public class DAOManager {
    private SessionFactory factory;
    private DBInitializer initializer;

    @Inject
    public DAOManager(SessionFactory factory, @Initializer DBInitializer initializer) {
        this.factory = factory;
        this.initializer = initializer;
    }

    public synchronized Session openNewSession() {
        return factory.openSession();
    }

    public void resetDB(DAO dao) {
        initializer.resetDatabase(dao);
    }

    @Bean
    @Professions
    public DAOEntityResolver<Profession, String> daoProfession() {
        return new DAOEntityResolver<>("FROM Profession", openNewSession());
    }

    @Bean
    @Races
    public DAOEntityResolver<Race, String> daoRaces() {
        return new DAOEntityResolver<>("FROM Race", openNewSession());
    }
}
