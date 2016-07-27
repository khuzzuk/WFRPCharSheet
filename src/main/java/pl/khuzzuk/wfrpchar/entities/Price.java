package pl.khuzzuk.wfrpchar.entities;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;

@RequiredArgsConstructor
@Immutable
public class Price {
    public final int gold;
    public final int silver;
    public final int lead;
}
