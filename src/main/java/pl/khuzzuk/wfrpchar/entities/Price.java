package pl.khuzzuk.wfrpchar.entities;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Embeddable;

@RequiredArgsConstructor
@Immutable
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Price {
    @NonNull
    @Setter
    @Getter
    private int gold;
    @NonNull
    @Setter
    @Getter
    private int silver;
    @NonNull
    @Setter
    @Getter
    private int lead;

    public static Price parsePrice(String s) {
        String[] values = s.split("\\|");
        Price price = new Price();
        if (values.length > 0) {
            price.gold = Integer.parseInt(values[0]);
            if (values.length > 1) {
                price.silver = Integer.parseInt(values[1]);
                if (values.length > 2) {
                    price.lead = Integer.parseInt(values[2]);
                }
            }
        }
        return price;
    }

    @Override
    public String toString() {
        return gold + "|" + silver + "|" + lead;
    }
}
