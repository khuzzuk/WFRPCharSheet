package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Immutable
@ToString(doNotUseGetters = true, exclude = "id")
@EqualsAndHashCode(exclude = "id")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Character.class)
public class Character implements Named<String>, Persistable, Documented {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    @NaturalId
    private String name;

    @Override
    public String toCsv() {
        return getName();
    }
}
