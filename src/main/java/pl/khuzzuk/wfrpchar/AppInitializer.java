package pl.khuzzuk.wfrpchar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.DAOConstants;
import pl.khuzzuk.wfrpchar.db.DAOManager;

@Component
public class AppInitializer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        System.out.println("finished");
        DAOManager manager = context.getBean(DAOManager.class);
        manager.resetDB();
        DAOConstants constants = context.getBean(DAOConstants.class);
        System.out.println(constants.getCharacters());
        System.out.println(constants.getAllItems());
    }
}
