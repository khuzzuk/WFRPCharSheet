package pl.khuzzuk.wfrpchar.db;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DAOManager {
    @PostConstruct
    private void reportInitialization(){
        System.out.println("DAO created");
    }
}
