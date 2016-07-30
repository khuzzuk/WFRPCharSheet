package pl.khuzzuk.wfrpchar.determinants;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Component
public class DeterminantFactory {
    @NotNull
    public Set<Determinant> createDeterminants(@NotNull @Valid String data) {
        Set<Determinant> determinants = new HashSet<>();
        String[] columns = data.split("\\|");
        if (columns.length==1 && columns[0].equals("")) return determinants;
        for (String s : columns) {
            String[] determinantString = s.split(",");
            Determinant determinant = createDeterminant(determinantString);
            if (determinantString.length == 3) {
                addExtensions(determinant, determinantString[2]);
            }
            determinants.add(determinant);
        }
        return determinants;
    }
    private void addExtensions(Determinant toDeterminant, String with) {
        String[] columns = with.split(":");
        for (String s : columns) {
            toDeterminant.addExtension(Extension.parseExtension(s));
        }
    }
    @NotNull
    private Determinant createDeterminant(@NotNull @Valid String[] data) {
        DeterminantsType type = DeterminantsType.valueOf(data[1]);
        Determinant determinant;
        if (type.getObjectType() == DeterminantsType.DetObjectType.PERCENTAGE) {
            determinant = new PercentageDeterminant();
        } else {
            determinant = new AbsoluteDeterminant();
        }
        determinant.setType(type);
        determinant.setBaseValue(Integer.parseInt(data[0]));
        return determinant;
    }
}
