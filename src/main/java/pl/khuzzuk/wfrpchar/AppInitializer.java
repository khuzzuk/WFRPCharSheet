package pl.khuzzuk.wfrpchar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.DAOPlayer;
import pl.khuzzuk.wfrpchar.entities.Player;

@Component
public class AppInitializer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        System.out.println("finished");
        DAOPlayer daoPlayer = (DAOPlayer) context.getBean("daoPlayer");
        Player player = new Player();
        player.setName("Name");
        daoPlayer.commit(player);
    }
}
