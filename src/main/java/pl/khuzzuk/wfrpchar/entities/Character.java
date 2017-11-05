package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

@RequiredArgsConstructor
@NoArgsConstructor
@ToString(doNotUseGetters = true, exclude = "id")
@EqualsAndHashCode(exclude = "id")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Character.class)
public class Character implements Named<String>, Persistable, Documented {
    private long id;
    @NonNull
    private String name;

    @Override
    public String toCsv() {
        return getName();
    }
}
