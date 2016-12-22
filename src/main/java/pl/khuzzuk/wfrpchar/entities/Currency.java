package pl.khuzzuk.wfrpchar.entities;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Immutable
public class Currency implements Named<String>, Persistable, Documented {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NonNull
    @NaturalId
    private String name;
    @Getter
    @Setter
    @NonNull
    private String gold;
    @Getter
    @Setter
    @NonNull
    private String silver;
    @Getter
    @Setter
    @NonNull
    private String lesser;
    @Getter
    @Setter
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
