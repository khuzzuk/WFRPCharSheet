package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EXT_TYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Extension {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NonNull
    protected int modifier;

    public static Extension parseExtension(String data) {
        Extension extension;
        String[] columns = data.split("-");
        if (columns[2].equals("true")) {
            extension = new ProfessionExtension(Integer.parseInt(columns[0]), Integer.parseInt(columns[1]));
        } else {
            extension = new MiscExtension(Integer.parseInt(columns[0]), columns[1]);
        }
        return extension;
    }

    public abstract String toCsv();
}
