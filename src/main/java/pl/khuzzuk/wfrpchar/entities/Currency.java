package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.ArrayList;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Currency.class)
public class Currency implements Named<String> {
    @NonNull
    private String name;
    @NonNull
    private String gold;
    @NonNull
    private String silver;
    @NonNull
    private String lesser;
    @NonNull
    private float value;
}
