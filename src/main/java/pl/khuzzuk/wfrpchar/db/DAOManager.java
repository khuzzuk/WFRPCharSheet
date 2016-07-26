package pl.khuzzuk.wfrpchar.db;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Component
@Named("daoManager")
public class DAOManager {
    @Inject
    @Getter
    private EntityManager entityManager;
    @PostConstruct
    private void reportInitialization(){
        System.out.println("DAO created");
    }
}
