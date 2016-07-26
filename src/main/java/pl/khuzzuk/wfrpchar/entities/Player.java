package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Player {
    @Id
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;
}
