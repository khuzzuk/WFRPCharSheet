package pl.khuzzuk.wfrpchar.entities.determinants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public abstract class Extension {
    @JsonIgnore
    private long id;
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
