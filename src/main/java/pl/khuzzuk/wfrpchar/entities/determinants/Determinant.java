package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;
import pl.khuzzuk.wfrpchar.entities.Labelled;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@DiscriminatorValue("0")
@ToString(exclude = "id")
public abstract class Determinant implements Labelled<DeterminantsType, String> {
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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Extension> extensions = new ArrayList<>();

    public int getActualValue() {
        int value = baseValue;
        for (Extension e : extensions) {
            value += e.getModifier();
        }
        return value;
    }

    public int getProfessionExtensionCount() {
        return extensions.stream()
                .filter(e -> e instanceof ProfessionExtension)
                .map(e -> (ProfessionExtension) e)
                .max((e1, e2) -> e1.getExpSequence() - e2.getExpSequence())
                .orElse(new ProfessionExtension()).getExpSequence();
    }

    public String toCSV() {
        return baseValue + "," + type + (extensions != null && extensions.size()>0 ? "," + extensionsToCsv() : "");
    }

    private String extensionsToCsv() {
        return extensions.stream().map(Extension::toCsv).collect(Collectors.joining(":"));
    }

    void addExtension(Extension extension) {
        extensions.add(extension);
    }

    @Override
    public DeterminantsType getLabel() {
        return type;
    }

    @Override
    public String getRepresentation() {
        return "" + getActualValue();
    }
}
