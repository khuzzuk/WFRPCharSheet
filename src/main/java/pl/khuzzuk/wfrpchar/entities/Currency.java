package pl.khuzzuk.wfrpchar.entities;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Immutable
public class Currency {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    @NonNull
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
}
