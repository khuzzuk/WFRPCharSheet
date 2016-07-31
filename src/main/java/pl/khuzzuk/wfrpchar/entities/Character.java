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
@EqualsAndHashCode
public class Character implements Nameable {
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
