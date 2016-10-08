package pl.khuzzuk.wfrpchar.entities.determinants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeterminantFactory {
    public static Set<Determinant> createDeterminants(String data) {
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
    private static void addExtensions(Determinant toDeterminant, String with) {
        String[] columns = with.split(":");
        for (String s : columns) {
            toDeterminant.addExtension(Extension.parseExtension(s));
        }
    }
    private static Determinant createDeterminant(String[] data) {
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

    static Determinant getDeterminantByName(String name) {
        String[] elements = name.replaceAll(" ", "").split(":");
        DeterminantsType determinantsType = DeterminantsType.forName(elements[0]);
        Determinant determinant = determinantsType.getDeterminant();
        determinant.setType(determinantsType);
        MiscExtension extension = new MiscExtension();
        extension.setModifier(Integer.parseInt(elements[1]));
        if (elements.length > 2) {
            extension.setDescription(elements[2]);
        }
        determinant.addExtension(extension);
        return determinant;
    }

    public static String getProfessionDeterminant(String entry) {
        String[] parts = entry.replaceAll(" ", "").split(":");
        List<String> fields = new ArrayList<>();
        fields.add("0");
        fields.add(DeterminantsType.forName(parts[0]).name());
        fields.add(getExtensions(parts[1]));
        return fields.stream().collect(Collectors.joining(","));
    }

    private static String getExtensions(String entry) {
        List<String> fields = new ArrayList<>();
        int extensionLevel = 0;
        for (int value = Integer.parseInt(entry); value > 0; value -= 10) {
            fields.add("10-" + ++extensionLevel + "-true");
        }
        return fields.stream().collect(Collectors.joining(":"));
    }
}
