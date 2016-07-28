package pl.khuzzuk.wfrpchar.determinants;

import lombok.*;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EXT_TYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Extension {
    private static final String MISC_DESC = "MISC";
    //private static final String PROF_DESC = "PROF";
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NonNull
    protected int modifier;

    public static Extension parseExtension(String[] columns) {
        Extension extension;
        if (columns[1].equals(MISC_DESC)) {
            extension = new MiscExtension(Integer.parseInt(columns[0]), columns[2]);
        } else {
            extension = new ProfessionExtension(Integer.parseInt(columns[0]), Integer.parseInt(columns[2]));
        }
        return extension;
    }
    abstract boolean isFromProfession();
}
