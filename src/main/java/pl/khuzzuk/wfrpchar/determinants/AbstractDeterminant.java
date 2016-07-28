package pl.khuzzuk.wfrpchar.determinants;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@DiscriminatorValue("1")
public abstract class AbstractDeterminant {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NonNull
    private DeterminantsType type;
    @Getter
    @Setter
    @NonNull
    private int baseValue;
    @NonNull
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Extension> extensions;

    public int getActualValue() {
        int value = baseValue;
        for (Extension e : extensions) {
            value += e.getModifier();
        }
        return value;
    }

    public int getProfessionExtensionCount() {
        return extensions.stream()
                .filter(Extension::isFromProfession)
                .map(e -> (ProfessionExtension) e)
                .max((e1, e2) -> e1.getExpSequence() - e2.getExpSequence())
                .orElse(new ProfessionExtension()).getExpSequence();
    }
}
