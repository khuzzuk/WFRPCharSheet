package pl.khuzzuk.wfrpchar.entities.determinants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public abstract class Determinant implements Labelled<DeterminantsType, String>, Named<String> {
    @JsonIgnore
    private long id;
    @NonNull
    private DeterminantsType type;
    @NonNull
    private int baseValue;
    @NonNull
    private List<Extension> extensions = new ArrayList<>();

    public static String determinantsToCsv(Collection<Determinant> determinants) {
        return determinants.stream().map(Determinant::toCSV).collect(Collectors.joining("|"));
    }

    @JsonIgnore
    public int getActualValue() {
        int value = baseValue;
        for (Extension e : extensions) {
            value += e.getModifier();
        }
        return value;
    }

    @JsonIgnore
    public int getProfessionExtensionCount() {
        return extensions.stream()
                .filter(e -> e instanceof ProfessionExtension)
                .map(e -> (ProfessionExtension) e)
                .max(Comparator.comparingInt(ProfessionExtension::getExpSequence))
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
    @JsonIgnore
    public DeterminantsType getLabel() {
        return type;
    }

    @Override
    @JsonIgnore
    public String getRepresentation() {
        return "" + getActualValue();
    }

    public static int getSumForType(Collection<Determinant> determinants, DeterminantsType type) {
        return (int) determinants.stream()
                .filter(d -> d.getLabel() == type)
                .collect(Collectors.summarizingInt(Determinant::getActualValue))
                .getSum();
    }

    @Override
    public String toString() {
        return type.getName() + ": " + getActualValue();
    }

    @Override
    @JsonIgnore
    public String getName() {
        return toString();
    }

    public static Set<Determinant> parseFromGui(Collection<String> determinants) {
        return determinants.stream().map(DeterminantFactory::getDeterminantByName).collect(Collectors.toSet());
    }
}
