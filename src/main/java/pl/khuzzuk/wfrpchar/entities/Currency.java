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
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Immutable
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Currency.class)
public class Currency implements Named<String>, Persistable, Documented {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    @NaturalId
    private String name;
    @NonNull
    private String gold;
    @NonNull
    private String silver;
    @NonNull
    private String lesser;
    @NonNull
    private float value;

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>())
                .add(name)
                .add(gold)
                .add(silver)
                .add(lesser)
                .add(value)
                .build();
    }
}
