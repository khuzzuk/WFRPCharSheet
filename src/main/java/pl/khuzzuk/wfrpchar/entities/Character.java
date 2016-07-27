package pl.khuzzuk.wfrpchar.entities;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Immutable
@ToString(doNotUseGetters = true, exclude = "id")
public class Character {
    @Id
            @GeneratedValue
            @Getter
            @Setter
    private int id;
    @NonNull
    @Getter
    @Setter
    private String name;
}
