package pl.khuzzuk.wfrpchar.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"pl.khuzzuk.wfrpchar.determinants", "pl.khuzzuk.wfrpchar.entities"})
public class GameConfig {
}
