package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("1")
public class ProfessionExtension extends Extension {
    @Getter
    @Setter
    @NonNull
    private int expSequence;

    public ProfessionExtension(int modifier, int expSequence) {
        super(modifier);
        this.expSequence = expSequence;
    }

    @Override
    public String toCsv() {
        StringBuilder csvEntry = new StringBuilder();
        csvEntry.append(modifier);
        return modifier + "-" + expSequence + "-true";
    }
}
