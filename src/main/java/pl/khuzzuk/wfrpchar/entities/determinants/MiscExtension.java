package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MiscExtension extends Extension {
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
