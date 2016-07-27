package pl.khuzzuk.wfrpchar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.DBInitializer;

@Component
public class AppInitializer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        System.out.println("finished");
        DBInitializer daoInitializer = (DBInitializer) context.getBean("DBInitializer");
        daoInitializer.resetDatabase();
    }
}
