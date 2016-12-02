package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@DiscriminatorValue("2")
public class MiscExtension extends Extension {
    @Getter
    @Setter
    @NonNull
    private String description;

    public MiscExtension(int modifier, String description) {
        super(modifier);
        this.description = description;
    }

    @Override
    public String toCsv() {
        return modifier + "-" + description + "-false";
    }
}
