package pl.khuzzuk.wfrpchar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
    }
}
