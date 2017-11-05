package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ProfessionExtension extends Extension {
    @NonNull
    private int expSequence;

    public ProfessionExtension(int modifier, int expSequence) {
        super(modifier);
        this.expSequence = expSequence;
    }

    @Override
    public String toCsv() {
        return modifier + "-" + expSequence + "-true";
    }
}
