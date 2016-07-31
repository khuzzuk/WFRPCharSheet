package pl.khuzzuk.wfrpchar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        /*DAOManager manager = context.getBean(DAOManager.class);
        DAO dao = context.getBean(DAO.class);
        manager.resetDB(dao);
        System.out.println(dao.getAllWeapons());
        System.out.println(dao.getAllWeapons().size());
        System.out.println(dao.getAllCharacters());*/
    }
}
