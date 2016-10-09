package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class Player implements Named<String>, Persistable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne
    private ProfessionClass professionClass;
}
