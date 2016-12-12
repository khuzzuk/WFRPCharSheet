package pl.khuzzuk.wfrpchar.entities;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Immutable
@ToString(doNotUseGetters = true, exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class Character implements Named<String>, Persistable {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @NonNull
    @Getter
    @Setter
    @NaturalId
    private String name;
}
